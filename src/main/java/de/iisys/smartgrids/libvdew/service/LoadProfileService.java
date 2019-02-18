package de.iisys.smartgrids.libvdew.service;

/**
 * Class that formats the OBIS number parameters for the load profile.
  */
public class LoadProfileService {

    /**
     * Formats the OBIS number parameters for the load profile.
     *
     * @param obisNumbers the OBIS numbers for the load profile
     * @return the OBIS numbers with a format
     */
    public static String formatObisNumberParameters(String... obisNumbers) {
        StringBuilder string = new StringBuilder();
        for (String obisNumber : obisNumbers) {
            string.append(")(").append(obisNumber);
        }
        return string.toString();
    }

}
