//
//  DetailViewController.swift
//  MP 2
//
//  Created by Darshan Dabhane on 6/16/16.
//  Copyright © 2016 Darshan Dabhane. All rights reserved.
//

import UIKit

class DetailViewController: UIViewController,UITableViewDelegate {
    
    @IBOutlet weak var table: UITableView!
    @IBOutlet weak var detailDescriptionLabel: UILabel!
    
    var objects : NSMutableDictionary? = [:]
    var selection : String?
    var selectedText: Double?
    var metricData: NSDictionary?
    var keys = [String]()
    var prettyDict : NSDictionary?
    var pDict: NSDictionary?
    var pStr = [String]()
    var sStr = [String]()
    
    @IBAction func doneButton(sender: AnyObject)
    {
        table.reloadData()
    }
    
//---------------------------------------------------------------------------------------------------------------------------------------
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int
    {
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int
    {
        return (objects?.count)!
    }
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell
    {
        
        guard let cell:DetailViewCell = table.dequeueReusableCellWithIdentifier("myCell") as? DetailViewCell else {
            return UITableViewCell()
        }
        
        cell.customLabel.text = keys[indexPath.row] as? String
        cell.customTextfield.text = String (objects?.allValues[indexPath.row] as! Double)
        
        //cell.customTextfield.text = String( metricData![(objects?.allKeys[indexPath.row] as? String)!])
        
        cell.customTextfield.tag = indexPath.row
        return cell
    }

//----------------------------------------------------------------------------------------------------------------------------------------
    
    var detailItem: AnyObject? {
        didSet {
            // Update the view.
            self.configureView()
        }
    }
  
    func configureView() {
        // Update the user interface for the detail item.
        if let detail = self.detailItem {
            if let label = self.detailDescriptionLabel {
                label.text = detail.description
            }
        }
    }
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        let path = NSBundle.mainBundle().pathForResource("conversions", ofType: "plist")
        let dict = NSMutableDictionary(contentsOfFile: path!)
        

        objects = dict!.objectForKey((detailItem?.description)!) as? NSMutableDictionary
        keys = objects?.allKeys as! [String]

        
        prettyDict = dict!.objectForKey("Pretty_Binding") as! NSDictionary
        pDict = prettyDict?.valueForKey((detailItem?.description)!) as! NSDictionary
        pStr = pDict?.allValues as! [String]
        sStr = keys
        
        
        self.title=detailItem?.description
      
        //metricData = objects
       
        selection = (detailItem?.description)!
        
        self.configureView()
    }
    
    
    func pretty_binding(currentField : Int){
        var value = [Double]()
        value = objects?.allValues as! [Double]
        for(var i=0; i<objects?.count; i += 1)
        {
                if(value[i] > 1.0)
                {
                    if(keys[i] == pStr[i])
                    {
                        keys[i] = keys[i]
                    }
                    else
                    {
                        keys[i] = pStr[i]
                    }
                }
                else
                {
                    keys[i] = sStr[i]
                }
        }
    }
    

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    


//-----------------------------------------------------------------------------------------------------------------------------------------
    
    var convert = ConversionLogic()
    
    @IBAction func conversionTextField(sender: UITextField) {
        if(sender.text != "")
        {
            selectedText = Double(sender.text!)
            
            if(selection=="Time" || selection=="Length" || selection=="Area" || selection=="Volume")
            {
                pretty_binding(sender.tag)
                objects = convert.convertMetric(selection!, selectedText: selectedText!,row: sender.tag)
            }
       }
        
        let indexPath = table.indexPathForCell(sender.superview!.superview as! UITableViewCell)
        var impCells = table.indexPathsForVisibleRows!
        impCells.removeAtIndex(indexPath!.row)
        table.reloadRowsAtIndexPaths(impCells ,withRowAnimation: .Automatic)
    }

    
  }

    





