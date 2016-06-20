package view;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**Listener para fecho de frames internas.
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.29
 */

public class JCloseMainIFrameListener implements InternalFrameListener {
   
    private final JInternalFrame frame;
    private final MainMenu parent;
    
    public JCloseMainIFrameListener(JInternalFrame frame, MainMenu parent) {
        this.frame=frame;
        this.parent=parent;
    }
    
    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
    }

    /**Rescrever método que define ações a quando fecho.
     * 
     * @param e, evento. 
     */
    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        parent.internalMainFrameCloseHandler(this.frame.getClass().getSimpleName());
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
    }
}
