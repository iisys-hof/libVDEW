package de.iisys.smartgrids.libvdew.protocol;

import de.iisys.libstate.StateGraph;
import de.iisys.smartgrids.libiec62056.client.IEC62056Client;
import de.iisys.smartgrids.libiec62056.message.ErrorMessage;
import de.iisys.smartgrids.libiec62056.protocol.IEC62056ProtocolModeC;
import de.iisys.smartgrids.libvdew.message.DateCommandMessage;
import de.iisys.smartgrids.libvdew.message.DateMessage;
import de.iisys.smartgrids.libvdew.message.LoadProfileCommandMessage;
import de.iisys.smartgrids.libvdew.message.LoadProfileMessage;
import de.iisys.smartgrids.libvdew.message.TimeCommandMessage;
import de.iisys.smartgrids.libvdew.message.TimeMessage;
import de.iisys.smartgrids.libvdew.message.VDEWIdentificationMessage;

/**
 * Class to register states and transitions of the VDEW protocol.
 *
  */
public class VDEWProtocol extends IEC62056ProtocolModeC {

    protected final String DATE_RECEIVE = "dateReceive";

    protected final String TIME_RECEIVE = "timeReceive";
   
    protected final String LOAD_PROFILE_RECEIVE = "loadProfileReceive";

    /**
     * Initializes {@link IEC62056ProtocolModeC#IEC62056ProtocolModeC(de.iisys.smartgrids.libiec62056.client.IEC62056Client)} with the 
     * given client . <br>
    */
    public VDEWProtocol(IEC62056Client client) {
        super(client);
    }

     /**
      * Initializes the constructor of {@link IEC62056ProtocolModeC#IEC62056ProtocolModeC(de.iisys.smartgrids.libiec62056.client.IEC62056Client, boolean)
      * and sets whether the programming mode should be entered or not.
      */
    public VDEWProtocol(IEC62056Client client, boolean enterProgrammingMode) {
        super(client, enterProgrammingMode);
    }

    @Override
    /**
     * Registers the states with the given protocol.
     */
    protected void registerStates(StateGraph protocol) {
        super.registerStates(protocol);

        protocol.overrideState(IDENTIFICATION_RECEIVE, (state) -> receiveAndProcess(state, VDEWIdentificationMessage.class, ErrorMessage.class));

        protocol.registerState(DATE_RECEIVE, (state) -> receiveAndProcess(state, DateMessage.class, ErrorMessage.class));
        protocol.registerState(TIME_RECEIVE, (state) -> receiveAndProcess(state, TimeMessage.class, ErrorMessage.class));

        protocol.registerState(LOAD_PROFILE_RECEIVE, (state) -> receiveAndProcess(state, LoadProfileMessage.class, ErrorMessage.class));
    }

    /**
     * Registers the transition.
     * @param protocol the protocol
     */
    @Override
    protected void registerTransitions(StateGraph protocol) {
        super.registerTransitions(protocol);

        protocol.registerTransition(PROGRAMMING_COMMAND_MESSAGE, DATE_RECEIVE, (transition) -> getPreparedMessage(transition.getSource()) instanceof DateCommandMessage);
        protocol.registerTransition(PROGRAMMING_COMMAND_MESSAGE, TIME_RECEIVE, (transition) -> getPreparedMessage(transition.getSource()) instanceof TimeCommandMessage);
        protocol.registerTransition(PROGRAMMING_COMMAND_MESSAGE, LOAD_PROFILE_RECEIVE, (transition) -> getPreparedMessage(transition.getSource()) instanceof LoadProfileCommandMessage);

        protocol.registerTransition(DATE_RECEIVE, PROGRAMMING_COMMAND_MESSAGE);
        protocol.registerTransition(DATE_RECEIVE, ERROR_RECEIVED, (transition) -> getReceivedMessage(transition.getSource()) instanceof ErrorMessage);

        protocol.registerTransition(TIME_RECEIVE, PROGRAMMING_COMMAND_MESSAGE);
        protocol.registerTransition(TIME_RECEIVE, ERROR_RECEIVED, (transition) -> getReceivedMessage(transition.getSource()) instanceof ErrorMessage);

        protocol.registerTransition(LOAD_PROFILE_RECEIVE, PROGRAMMING_COMMAND_MESSAGE);
        protocol.registerTransition(LOAD_PROFILE_RECEIVE, ERROR_RECEIVED, (transition) -> getReceivedMessage(transition.getSource()) instanceof ErrorMessage);
    }

}
