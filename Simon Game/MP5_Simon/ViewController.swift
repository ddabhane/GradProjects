//
//  ViewController.swift
//  MP5_Simon
//
//  Created by Darshan Dabhane on 7/1/16.
//  Copyright © 2016 Darshan Dabhane. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    
    
    var sequence = [Int]()
    var guessID = 0
    var length: Int = 1
    var seqLen: Int = 1
    
    
    
    @IBOutlet weak var messageLabel: UILabel!
    
    @IBOutlet var buttons: [UIButton]!
    
    @IBOutlet weak var sequenceLength: UITextField!
    
    @IBAction func sequenceLen(sender: UITextField)
    {
        if sequenceLength.text != ""
        {
            length = Int(sequenceLength.text!)!
        }
    }
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        messageLabel.text = "Enter the sequence length and double tap to Start!"
    }
  
    //------------------------------------------------------------------------------------------------------------------------------------------------------
    
    @IBAction func buttonTapped(sender: UIButton) {
        if sequenceLength.text != ""
        {
        
        if buttons[sequence[guessID]] == sender {
            messageLabel.text = "Good guess"
            guessID += 1
        
        if guessID == seqLen  {
            
            seqLen = seqLen + 1
            NSThread.sleepForTimeInterval(1.0)
            guessID = 0
            sequence.removeAll()
           
            
        if (seqLen > length){
            messageLabel.text = "You WIN! (Enter the sequence length and double tap to restart)"
            sequence.removeAll()
            seqLen = 1
            length = Int(sequenceLength.text!)!
            sequenceLength.text = ""
            sequenceLength.placeholder = "Enter the sequence length"
            }
            
            else{
                self.startGame(self)
            }
          }
        }
           
        else {
            messageLabel.text = "Wrong!"
            guessID = 0
            let queue = dispatch_get_global_queue(QOS_CLASS_USER_INITIATED, 0)
                
            dispatch_async(queue) {
            // blink all buttons and restart game
                    
            dispatch_async(dispatch_get_main_queue()) {
                for btn in self.buttons {
                btn.highlighted = true
                    }
                }
            NSThread.sleepForTimeInterval(1.0)
            dispatch_async(dispatch_get_main_queue()) {
                for btn in self.buttons {
                btn.highlighted = false
                    }
                }
            NSThread.sleepForTimeInterval(1.0)
            dispatch_async(dispatch_get_main_queue()) {
                self.flashSequence()
                }
            }
         }
      }
    }
    
 //-----------------------------------------------------------------------------------------------------------------------------------------------------
    func flashSequence()
    {
        let queue = dispatch_get_global_queue(QOS_CLASS_USER_INITIATED, 0)
        self.messageLabel.text = "Watch Carefully!"
        
        dispatch_async(queue)
        {
            for buttonIndex in self.sequence
            {
               
                    dispatch_async(dispatch_get_main_queue())
                    {
                        self.buttons[buttonIndex].highlighted = true
                    }
                    
                    NSThread.sleepForTimeInterval(1.2)
                    
                    dispatch_async(dispatch_get_main_queue())
                    {
                        self.buttons[buttonIndex].highlighted = false
                    }
                    
                    NSThread.sleepForTimeInterval(1.2)

        }
        dispatch_async(dispatch_get_main_queue())
        {
            self.messageLabel.text = "Tap out the sequence!"
        }
      }
    }
    
    
  //-------------------------------------------------------------------------------------------------------------------------------------------------------
    
    func generateSequence()
    {
        if seqLen<=length
        {
            for _ in 0..<seqLen
            {
              sequence.append(Int(arc4random_uniform(4)))
            }
        }
        
        print(sequence)
    }
 //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    @IBAction func startGame(sender: AnyObject)
    {
        if sequenceLength.text != ""
        {
            guessID = 0
            generateSequence()
            flashSequence()
        }
    }
    
    override func touchesBegan(touches: Set<UITouch>, withEvent event: UIEvent?)
    {
        self.view.endEditing(true)
        super.touchesBegan(touches,withEvent: event)
    }
    
}

