package de.iisys.smartgrids.libvdew.message.content;

import de.iisys.smartgrids.libiec62056.message.content.ReadCommand;
import de.iisys.smartgrids.libvdew.service.DateTimeService;
import java.util.Calendar;

/**
 * Class to delete the load profile.
 *
  */
public class DeleteLoadProfile extends ReadCommand {

    /**
     * Used as invoking purposes.
     */
    public DeleteLoadProfile() {
        this(1);
    }

    /**
     * Deletes the load profile with the given profile number.
     *
     * @param profileNumber the number of the load profile
     */
    public DeleteLoadProfile(int profileNumber) {
        this(profileNumber, null);
    }

    /**
     * Deletes the load profile with the given profile number and the end date.
     *
     * @param profileNumber the number of the load profile
     * @param endDate the end date of the load profile
     */
    public DeleteLoadProfile(int profileNumber, Calendar endDate) {
        this(profileNumber, false, endDate);
    }

    /**
     * Deletes the load profile with the given profile number and whether the
     * load profile is to disturbed.
     *
     * @param profileNumber the number of the load profile
     * @param toDisturbed whether the load profile is disturbed or not
     */
    public DeleteLoadProfile(int profileNumber, boolean toDisturbed) {
        this(profileNumber, toDisturbed, null);
    }

    /**
     * Deletes the load profile with the given profile number, whether the load
     * profile is to disturbed or the end of the load profile.
     *
     * @param profileNumber the number of the load profile
     * @param toDisturbed whether the load profile is disturbed or not
     * @param endDate the end date of the profile number
     */
    public DeleteLoadProfile(int profileNumber, boolean toDisturbed, Calendar endDate) {
        super(
                String.format("P.%02d", profileNumber),
                String.format(";%s",
                        toDisturbed
                                ? "#"
                                : endDate != null ? DateTimeService.formatDateTimeShortWithSeason(endDate) : ""
                )
        );
    }

}
