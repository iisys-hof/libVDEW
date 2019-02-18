package de.iisys.smartgrids.libvdew.message.content;

import de.iisys.smartgrids.libiec62056.message.content.ReadCommand;
import de.iisys.smartgrids.libvdew.service.DateTimeService;
import de.iisys.smartgrids.libvdew.service.LoadProfileService;
import java.util.Calendar;

/**
 * Class to read the load profile.
 *
  */
public class ReadLoadProfile extends ReadCommand {

    /**
     * Gives load profile 1 with the given obis numbers to
     * {@link #ReadLoadProfile(int, boolean, boolean, java.lang.String...)}.
     *
     * @param obisNumbers OBIS numbers of the load profile
     */
    public ReadLoadProfile(String... obisNumbers) {
        this(1, null, null, obisNumbers);
    }

    /**
     * Gives the given load profile number and obis numbers to
     * {@link #ReadLoadProfile(int, boolean, boolean, java.lang.String...)}.
     *
     * @param profileNumber the number of the load profile
     * @param obisNumbers the OBIS numbers of the load profile
     */
    public ReadLoadProfile(int profileNumber, String... obisNumbers) {
        this(profileNumber, null, null, obisNumbers);
    }

    /**
     * Gives the given load profile number, starting date, ending date and obis numbers to
     * {@link #ReadLoadProfile(int, boolean, java.util.Calendar, boolean, java.util.Calendar, java.lang.String...)}.
     * @param profileNumber the profile number of the load profile
     * @param startDate the start date of the load profile
     * @param endDate the end date of the load profile
     * @param obisNumbers the obis numbers of the load profile
     */
    public ReadLoadProfile(int profileNumber, Calendar startDate, Calendar endDate, String... obisNumbers) {
        this(profileNumber, false, startDate, false, endDate, obisNumbers);
    }

    /**
     * Gives the given load profile number, whether it was disturbed and wheters it is disturbed to
     * and the obis numbers to
     * {@link #ReadLoadProfile(int, boolean, java.util.Calendar, boolean, java.util.Calendar, java.lang.String...)}.
     *
     * @param profileNumber the profile number of the load profile
     * @param fromDisturbed from disturbed
     * @param toDisturbed to disturbed
     * @param obisNumbers the OBIS numbers of the load profile
     */
    public ReadLoadProfile(int profileNumber, boolean fromDisturbed, boolean toDisturbed, String... obisNumbers) {
        this(profileNumber, fromDisturbed, null, toDisturbed, null, obisNumbers);
    }

    /**
     * Reads the load profile with the given profile number, the period of the
     * disorder and the period of the date and OBIS numbers of the load profile.
     *
     * @param profileNumber the profile number
     * @param fromDisturbed from disturbed
     * @param startDate starting date
     * @param toDisturbed to disturbed
     * @param endDate ending date
     * @param obisNumbers obis numbers
     */
    public ReadLoadProfile(int profileNumber, boolean fromDisturbed, Calendar startDate, boolean toDisturbed, Calendar endDate, String... obisNumbers) {
        super(
                String.format("P.%02d", profileNumber),
                String.format("%s%s",
                        String.format("%s;%s",
                                fromDisturbed
                                        ? "#"
                                        : startDate != null ? DateTimeService.formatDateTimeShortWithSeason(startDate) : "",
                                toDisturbed
                                        ? "#"
                                        : endDate != null ? DateTimeService.formatDateTimeShortWithSeason(endDate) : ""
                        ),
                        LoadProfileService.formatObisNumberParameters(obisNumbers))
        );
    }

}
