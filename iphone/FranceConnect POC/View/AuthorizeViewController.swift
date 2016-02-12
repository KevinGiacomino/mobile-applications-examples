//
//  AuthorizeViewController.swift
//  FranceConnect POC
//
//  Created by Louis Davin on 04/11/2015.
//  Copyright © 2015 OCTO Technology. All rights reserved.
//

import UIKit

class AuthorizeViewController: UIViewController, UIWebViewDelegate {
    
    static let kLoginError = 0
    static let kUserInfoError = 1
    static let kContinueSegue = "AuthorizeToTabBarSegue"
    
    let state:String = NSUUID().UUIDString
    let nonce:String = NSUUID().UUIDString
    
    @IBOutlet weak var webView: UIWebView!
    var userInfo: UserInfo?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if let url = FranceConnectURLBuilder.buildAuthorizeUrlWith(state, nonce: nonce) {
            webView.loadRequest(NSURLRequest(URL: url))
        } else {
            dismissAndShowError(AuthorizeViewController.kLoginError)
        }
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == AuthorizeViewController.kContinueSegue {
            let tabBarController = segue.destinationViewController as! TabBarController
            tabBarController.userInfo = userInfo
        }
    }
    
    func webView(webView: UIWebView, shouldStartLoadWithRequest request: NSURLRequest, navigationType: UIWebViewNavigationType) -> Bool {
        if let url = request.URL {
            
            let urlString = url.absoluteString
            if urlString.hasPrefix(Constants.Backend.callbackUrl) {
                
                var callbackCode:String? = nil
                var callbackState:String? = nil
                
                let urlComponents = NSURLComponents(URL: url, resolvingAgainstBaseURL: false)
                if let queryItems:[NSURLQueryItem] = urlComponents?.queryItems {
                    for queryItem in queryItems {
                        if "code" == queryItem.name {
                            callbackCode = queryItem.value
                        } else if "state" == queryItem.name {
                            callbackState = queryItem.value
                        }
                    }
                }
                
                if let unwrappedCode = callbackCode, unwrappedState = callbackState {
                    checkCallbackVariables(unwrappedState, callbackCode:unwrappedCode)
                } else {
                    dismissAndShowError(AuthorizeViewController.kLoginError)
                }
                
                return false
            }
        }
        
        return true
    }
    
    func checkCallbackVariables(callbackState: String, callbackCode: String) {
        if state == callbackState && callbackCode.characters.count > 1 {
            fetchUserInfo(callbackCode)
        } else {
            dismissAndShowError(AuthorizeViewController.kLoginError)
        }
    }
    
    func fetchUserInfo(code: String) {
        let alertView = UIAlertView(title: "Chargement", message: nil, delegate: nil, cancelButtonTitle: nil)
        let indicator = UIActivityIndicatorView(activityIndicatorStyle: UIActivityIndicatorViewStyle.Gray)
        indicator.startAnimating()
        alertView.setValue(indicator, forKey: "accessoryView")
        
        alertView.show()
        
        UserInfoService().fetchUserInfo(code, nonce: nonce, successCallback: { (userInfo) in
                alertView.dismissWithClickedButtonIndex(0, animated: true)
                self.userInfo = userInfo
                self.performSegueWithIdentifier(AuthorizeViewController.kContinueSegue, sender: nil)
            },
            errorCallback: {
                alertView.dismissWithClickedButtonIndex(0, animated: true)
                self.dismissAndShowError(AuthorizeViewController.kUserInfoError)
            })
    }
    
    func dismissAndShowError(errorCode: Int) {
        // TODO Do something
    }
    
}
