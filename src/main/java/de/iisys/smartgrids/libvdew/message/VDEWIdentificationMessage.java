package de.iisys.smartgrids.libvdew.message;

import de.iisys.libinterface.message.annotation.MessageTemplate;
import de.iisys.smartgrids.libiec62056.message.IdentificationMessage;
import de.iisys.libinterface.message.annotation.Callback;

/**
 * Class to identify characters according to VDEW Specifications 2.1.2, 10.3.1
 * for valid characters. Also sets extended mode if proposed.
  */
@MessageTemplate("/{3:manufacturerIdentification}{1:baudRateCharacter}\\\\{1:identificationCharacter}{identification}<CR><LF>")
public class VDEWIdentificationMessage extends IdentificationMessage {

    @Callback("checkIdentificationCharacter")
    private char identificationCharacter;
    private boolean extendedModeC;

    /**
     * Check identification Character according to VDEW Specifications 2.1.2,
     * 10.3.1 for valid character. Also sets extended mode if proposed.
     *
     * @return true if the character is valid
     */
    protected boolean checkIdentificationCharacter() {
        extendedModeC = identificationCharacter == '@';
        return identificationCharacter >= 0x30 && identificationCharacter <= 0x7F;
    }

    @Override
    public char getIdentificationCharacter() {
        return identificationCharacter;
    }

    public boolean isExtendedModeC() {
        return extendedModeC;
    }

}
