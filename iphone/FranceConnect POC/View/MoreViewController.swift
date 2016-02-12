//
//  MoreViewController.swift
//  FranceConnect POC
//
//  Created by Louis Davin on 20/11/2015.
//  Copyright © 2015 OCTO Technology. All rights reserved.
//

import UIKit

class MoreViewController : UIViewController {

    
    @IBAction func didTapLogoutButton(sender: AnyObject) {
        let alertView = UIAlertView(title: "Chargement", message: nil, delegate: nil, cancelButtonTitle: nil)
        let indicator = UIActivityIndicatorView(activityIndicatorStyle: UIActivityIndicatorViewStyle.Gray)
        indicator.startAnimating()
        alertView.setValue(indicator, forKey: "accessoryView")
        
        alertView.show()
        
        if let cookies = NSHTTPCookieStorage.sharedHTTPCookieStorage().cookies {
            for cookie in cookies {
                NSHTTPCookieStorage.sharedHTTPCookieStorage().deleteCookie(cookie)
            }
        }
        
        UserInfoService().logoutUser({
            alertView.dismissWithClickedButtonIndex(0, animated: true)
            
            let navigationController:UINavigationController? = self.tabBarController?.presentingViewController as? UINavigationController
            self.dismissViewControllerAnimated(true, completion: { () -> Void in
                navigationController!.popToRootViewControllerAnimated(false)
            })
        })
    }
}