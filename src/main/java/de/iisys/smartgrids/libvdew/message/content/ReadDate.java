/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.iisys.smartgrids.libvdew.message.content;

import de.iisys.smartgrids.libiec62056.message.content.ReadCommand;

/**
 * Class to process the date.
  */
public class ReadDate extends ReadCommand {

    public ReadDate() {
        super("0.9.2", null);
    }

}
