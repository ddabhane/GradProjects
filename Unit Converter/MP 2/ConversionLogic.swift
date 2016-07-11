//
//  ConversionLogic.swift
//  MP 2
//
//  Created by Darshan Dabhane on 6/24/16.
//  Copyright © 2016 Darshan Dabhane. All rights reserved.
//

import Foundation

class ConversionLogic
{
    var dictionaryValue:NSMutableDictionary? = [:]
    var metricUnitArray:[String]?
    var thevalues = [Double]()
    var prettyValues = [String]()
    
    
   // func getSomething() -> [Double]{
     //   return thevalues
   // }

    
    func convertMetric(selection:String, selectedText:Double, row:Int) -> NSMutableDictionary
    {
       
        let path = NSBundle.mainBundle().pathForResource("conversions", ofType: "plist")
        
        let dict = NSMutableDictionary(contentsOfFile: path!)
        
        let dictionaryItems = dictionaryValue?.count
        
        if(dictionaryItems > 0)
        {
            dictionaryValue = [:]
        }
        
        
        dictionaryValue = dict?.valueForKey(selection) as? NSMutableDictionary
       
        metricUnitArray = dictionaryValue?.allKeys as? [String]
        
        let baseUnits = dict?.valueForKey("Base_Types") as! NSMutableDictionary
       
        let baseUNIT = baseUnits[selection] as! String
        
        let currentUnit = dictionaryValue?.allKeys[row] as! String
        
        let currentUnitVALUE = dictionaryValue?.allValues[row] as! Double
        
        let intoBaseType = selectedText * currentUnitVALUE
        
        for metricUnit in metricUnitArray!
        {
            if metricUnit == baseUNIT
            {
                dictionaryValue?.setValue(intoBaseType, forKey: baseUNIT)
               
            }
            else if metricUnit == currentUnit
            {
                dictionaryValue?.setValue(selectedText, forKey: currentUnit)
            }
            else
            {
               let value = dictionaryValue?.valueForKey(metricUnit) as! Double
                
               let convertedValue = Double(round(10000*(intoBaseType / value))/10000)
                
               dictionaryValue?.setValue(convertedValue, forKey: metricUnit)
            }
        }
        
        print (dictionaryValue)
        return dictionaryValue!
    }
    
    func temperatureConversion(selection:String, selectedText:Double, row:Int)  -> NSMutableDictionary
    {
        var array = [Double]()
        
        var dump: Double = 0.0
        
        var currentVALUE = [Double]()
        
        let currentUnit = dictionaryValue?.allKeys[row] as! String
        
        let path = NSBundle.mainBundle().pathForResource("conversions", ofType: "plist")
        
        let dict = NSMutableDictionary(contentsOfFile: path!)
        
        let dictionaryItems = dictionaryValue?.count
        
        if(dictionaryItems > 0)
        {
            dictionaryValue = [:]
        }
        dictionaryValue = dict?.valueForKey(selection) as? NSMutableDictionary
        
        metricUnitArray = dictionaryValue?.allKeys as? [String]
        
        let baseUnits = dict?.valueForKey("Base_Types")?.valueForKey(selection) as! String
        
        currentVALUE = dictionaryValue?.allValues[row] as! Array
        
        
        let value = Double(round(1000 * ((selectedText + currentVALUE[1]) * currentVALUE[0] ))/1000)
        
        for item in metricUnitArray!
        {
            if item == baseUnits
            {
                dictionaryValue?.setValue(value, forKey : item)
            }
        
        else if item == currentUnit
        {
            dictionaryValue?.setValue(selectedText, forKey: item)
        }
        else
        {
            array = dictionaryValue?.valueForKey(item) as! Array
            dump = Double(round(1000 * (value / array[0] - array[1]) / 1000))
            dictionaryValue?.setValue(dump, forKey : item)
        }
        
    }
    
        return dictionaryValue!
    }
    
    
    
}
        
        
        
        
        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 