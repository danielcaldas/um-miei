import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.DefaultCaret;

/**
 * Interface do Stand Vitoria.
 * @author Daniel Caldas, José Cortez, Susana Mendes, Xavier Rodrigues
 * @version 2015.05.06
 */

public class Console extends JPanel implements KeyListener, CaretListener {

	private static final long serialVersionUID = -7045856491686152610L;
	private static final String PROMPT = "?- ";

	// Componentes swing
    private JScrollPane scrollPane;
    private JTextArea consoleTextPane;
    private static JButton loadButton;
    private static JPanel contentPane;
    
    // Variáveis de instância
    private PrologStub sicstus; // Classe de comunicação com o SICStus
    private int startIndex;
    private boolean sicstusRunning = false;
    private String filePath;

    // Stack para fazer chache de comandos utilizados durante a sessão
    private int sp;
    private ArrayList<String> stack;
    
    
    /**
     * Função main de arranque da interface.
     * @param args argumentos.
     */
    public static void main(String[] args) {    	
    	// Criar frame principal
        JFrame frame = new JFrame("Stand Vitoria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        String s = System.getProperty("user.dir");
        
        contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(1, 1, 1, 1));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
		
		// Criar painel para suporte do butão carregar ficheiros
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));        
        
        // Criar logo para frame principal
        JLabel logo = new JLabel();
        ImageIcon img = new ImageIcon(s+"\\vitoria.png");
        logo.setIcon(img);        
        
        contentPane.add(new Console());
        contentPane.add(logo, BorderLayout.WEST);
        
        bottom.add(loadButton);       
        
        contentPane.add(bottom,BorderLayout.SOUTH);
        
        frame.pack();
        
        // Centrar main frame do stand Vitoria
        frame.setLocationRelativeTo(null); 
        frame.setSize(800, 500);
        frame.setVisible(true);       
    }

    
    /*----------------------------------------
       INICIALIZAÇÃO
     -----------------------------------------*/
    public Console() {
        super();
        
        // Incializar Stack de comandos
        sp=0;
        stack = new ArrayList<String>();
        
        initComponents();

        // Criar área de texto
        consoleTextPane = new JTextArea();
        DefaultCaret caret = (DefaultCaret)consoleTextPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        consoleTextPane.append(PROMPT);
        consoleTextPane.setBorder(null);
        
        // Permite continuar a escrever texto nas linhas seguintes como num terminal normal
        consoleTextPane.setLineWrap(true);
        consoleTextPane.setWrapStyleWord(false);

        // Posição inicial do carater
        startIndex = consoleTextPane.getText().length();
        consoleTextPane.setCaretPosition(startIndex);

        // Adicionar Listeners para teclado
        consoleTextPane.addCaretListener(this);
        consoleTextPane.addKeyListener(this);

        // Scrollbar, vertical claro
        scrollPane = new JScrollPane(consoleTextPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(null);

        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.setPreferredSize(new Dimension(600, 400));
        panelCenter.add(scrollPane, BorderLayout.CENTER);

        add(panelCenter, BorderLayout.CENTER);
    }
    
    /**
     * Iniciar outras componentes do GUI (butão carregar ficheiro)
     */
    public void initComponents() {
    	String s = System.getProperty("user.dir");
    	ImageIcon load = new ImageIcon(s+"\\upload.png");
    	loadButton = new JButton();        
        loadButton.setText("  Carregar código");
        loadButton.setPreferredSize(new Dimension(180, 42));
        loadButton.setIcon(load);
        loadButton.setHorizontalAlignment(SwingConstants.LEFT);
        
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickLoadButton(evt);
            }
        });
    }

    
    /*-----------------------------------------------------
       LISTENERS E OUTROS MÉTODOS AUXILIARES
     -----------------------------------------------------*/
    
    @Override
    public void keyTyped(KeyEvent e) {
        // All processing in keyPressed
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // All processing in keyPressed
    }

    /**
     * Listener para o teclado para tratar da escrita de novos comandos.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_ENTER: // Foi primido o ENTER
            	
                String command = consoleTextPane.getText().substring(startIndex);
                           
                if(command.contains("load")){ // estamos a carregar um ficheiro
                	String fileToLoad=null;
                	if(filePath!=null){
                		fileToLoad=filePath;
                	} else {
                		String[] tokens = command.split(" ");
                		fileToLoad = tokens[1].trim();
                	}
                	if(fileToLoad!=null){
                		sicstus = new PrologStub(fileToLoad);
                		if(sicstus.loadOK()){
                			sicstusRunning=true;
                			consoleTextPane.append(System.lineSeparator()+"   Load successful.\n");
                		} else {
                			consoleTextPane.append(System.lineSeparator()+"   Ficheiro inexistente.\n");
                		}
                		
                	} else {
                		consoleTextPane.append(System.lineSeparator()+"Erro.\n");
                	}
                } else if(sicstusRunning==true){            
	                
	                if (!command.isEmpty()) {
	                    String r = sicstus.checkError(command);
	                    if(r.equals(PrologStub.OK)){
	                    	String answ = sicstus.interpret(command);
	                    	consoleTextPane.append(System.lineSeparator()+answ);
	                    } else {
	                    	// Ocorreu um erro
	                    	consoleTextPane.append(System.lineSeparator()+r);
	                    }
	                }
                } else {
                	consoleTextPane.append(System.lineSeparator()+"   Ainda não foi carregado nenhum ficheiro.\n");
                }
                
                // Atualizar consola e fazer append de novo símbolo de começo de linha (?-)
                consoleTextPane.append(System.lineSeparator() + PROMPT);
                startIndex = consoleTextPane.getText().length();
                
                stack.add(command); sp++;
                
                // Consumir a tecla primida para não processarmos mais texto de futuro
                e.consume();
                break;
                
            case KeyEvent.VK_BACK_SPACE:
                // Ter a certeza de que se trata de um DELETE válido
                if (consoleTextPane.getCaretPosition() <= startIndex) {
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
                break;
               
            case KeyEvent.VK_UP:
            	// Consultar histórico de comandos previamente utilizados
            	if(sp>0){                    
            		sp--;
            		consoleTextPane.append(stack.get(sp));
            		e.consume();
            		
            		if(sp==0) sp = stack.size();
            	} else break;
            	break;
                
            default: break;
        }
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        // Temos de ter a certeza que a posição do caratér é uma posição válida
        if (e.getDot() < startIndex) {
            consoleTextPane.setCaretPosition(startIndex);
            Toolkit.getDefaultToolkit().beep();
        }
    }
    
    /**
     * Método que trata a ação de clique no butão load.
     * @param evt.
     */
    private void onClickLoadButton(java.awt.event.ActionEvent evt){
    	// Inicializar o filechooser na diretoria de trabalho
    	JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        chooser.setMultiSelectionEnabled(true);
        int returnValue = chooser.showOpenDialog(null);
        
        if(returnValue == JFileChooser.APPROVE_OPTION){
            filePath = chooser.getSelectedFile().getPath();
            System.out.println(filePath);
            consoleTextPane.append(System.lineSeparator()+"   Nome do ficheiro em memória.\n   Escrever \"load\" para carregar ficheiro no SICStus.\n");
            consoleTextPane.append(System.lineSeparator() + PROMPT);
        }
    }
}