//
//  HistoryViewController.swift
//  FranceConnect POC
//
//  Created by Louis Davin on 18/11/2015.
//  Copyright © 2015 OCTO Technology. All rights reserved.
//

import UIKit

class HistoryViewController: UIViewController {
    
    @IBOutlet weak var webView: UIWebView!
    
    override func viewDidLoad() {
        webView.loadRequest(NSURLRequest(URL: FranceConnectURLBuilder.buildHistoryUrl()!))
    }
}
