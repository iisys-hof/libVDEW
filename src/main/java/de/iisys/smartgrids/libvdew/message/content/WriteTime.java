/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.iisys.smartgrids.libvdew.message.content;

import de.iisys.smartgrids.libiec62056.message.content.WriteCommand;
import de.iisys.smartgrids.libvdew.service.DateTimeService;
import java.util.Calendar;

/**
 * Class process the time.
 *
  */
public class WriteTime extends WriteCommand {

    /**
     * Initializes {@link WriteCommand#WriteCommand(java.lang.String, java.lang.String)}
     * @see WriteCommand
     * @param time the time
     */
    public WriteTime(Calendar time) {
        super("0.9.1", DateTimeService.formatTimeWithSeason(time));
    }

}
