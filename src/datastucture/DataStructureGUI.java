// Package where the Java Class is located
package datastucture;

// Imports for the Java Program.
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class DataStructureGUI extends javax.swing.JFrame {

    Font IronShark;
    StartupSound sound = new StartupSound();
    URL startup = getClass().getClassLoader().getResource("BTS.wav");


    /*/
            PART 1 : INFIX TO POSTFIX CONVERSION METHOD

            This part of the Code Converts Infix To Postfix Notation
        
    /*/
 /*/
            Declare Stack Table Model where Token, Action, Result, and Stack is Defined.
            Method is set to Public
    /*/
    public InfixToPostfixModel PostfixModel;
    public InfixToPrefixModel PrefixModel;
    
   
    // Action Listener for Postfix Convertion Algorithm.
    public class PostfixConversionListener implements ActionListener {

        private final DataStructureGUI PostfixView;

        private final InfixToPostfixModel PostfixModel;

        // Setters for the Postfix Expression.
        public PostfixConversionListener(DataStructureGUI view, InfixToPostfixModel model) {
            PostfixView = view;
            PostfixModel = model;
        }

        /*/
            Get User Input via JTextField.
        /*/
        @Override
        public void actionPerformed(ActionEvent event) {

            String expression = InfixInput1.getText();

            if (expression.isEmpty()) {
                DisplayInput1.setText("NOTHING TO CONVERT!");
            } else {

                DisplayInput1.setText(expression);
                String infixExpression = PostfixView.getInfixExpression();
                PostfixModel.setInfixExpression(infixExpression);
                PostfixModel.setPostfixExpression(infixToPostfix(infixExpression));
                PostfixView.setPostfixExpression();
            }
        }

        /*/
            Infix To Postfix Private Method for Conversion
        /*/
        private String infixToPostfix(String expression) {

            // Removes All Rows in the JTable (Stack Table) after Each User Input.
            removeAllRows(PostfixModel.getTableModel());

            // Declaration String result scans each Character of the User Input (Infix Notation)
            String result = "";
            Stack<Character> stack = new Stack<>();

            // For Loop for Scanning the Characters of the String(User Input).
            for (int i = 0; i < expression.length(); ++i) {

                char c = expression.charAt(i);

                /*/ 
                    If the Scanned Character is a Letter or Digit, It will Pop the Operand in the Stack and Output
                    it into the result.
                /*/
                if (Character.isLetterOrDigit(c)) {

                    result += c;
                    updateResult(stack, c, result);

                } /*/
                    If the Scanned Character is a Open Parenthesis '(', It will Push the Operand into the Stack.
                /*/ else if (c == '(') {

                    stack.push(c);
                    pushOperator(stack, c, result);

                } else if (c == ')') {

                    //This Ignores the ')' from the stack.
                    continue;

                } else {
                    /*/
                    If an Operator is encountered, it will output into the stack and Concatinate the Operators in the Stack.
                    /*/
                    while (!stack.isEmpty() && precedence(c)
                            <= precedence(stack.peek())) {

                        result = popOperator(stack, result, true);
                    }

                    // Pushes the Operator into the stack.
                    pushOperator(stack, c, result);
                }

            }
            // pop all the operators from the stack.

            int size = stack.size();
            for (int index = 0; index < size; index++) {
                result = popOperator(stack, result, false);
            }

            return result;

        }

        /*/
                Methods "updateResult", "pushOperator" and "popOperator updates the Result of the JTable (Stack Table).
        /*/
        private void updateResult(Stack<Character> stack, char c, String result) {
            String token = Character.toString(c);
            String action = "Add " + c + " to the result";
            insertRow(PostfixModel.getTableModel(), token, action, result,
                    displayStack(stack));
        }

        private void pushOperator(Stack<Character> stack, char c, String result) {
            stack.push(c);
            String token = Character.toString(c);
            String action = "Push " + c + " to stack";
            insertRow(PostfixModel.getTableModel(), token, action, result,
                    displayStack(stack));
        }

        private String popOperator(Stack<Character> stack, String result,
                boolean displayToken) {
            char o = stack.pop();
            String token = "";
            String action;

            if (displayToken) {
                token = Character.toString(o);
            }

            if (o == '(') {
                action = "Pop " + o + " from stack";
            } else {
                result += o;
                action = "Pop " + o + " from stack and add to result";
            }

            insertRow(PostfixModel.getTableModel(), token, action, result,
                    displayStack(stack));
            return result;
        }

        /*/
            This Sets the Precedence Level of Operators.
            Precedence Level is definde by "return 1;", "return 2;" and "return 3;".
            "return -1;" states that the Stack Is Empty.
            
        /*/
        private int precedence(char o) {
            switch (o) {
                case '+', '-' -> {
                    return 1;
                }
                case '*', '/' -> {
                    return 2;
                }
                case '^' -> {
                    return 3;
                }
                case '(', ')' -> {
                    return 9;
                }
            }
            return -1;
        }

        // Displays the Stack Table
        private String displayStack(Stack<Character> stack) {
            String result = "";
            for (int index = (stack.size() - 1); index >= 0; index--) {
                result += stack.get(index);
            }
            return result;
        }

        // Declared Private Method that removes all of the contents of the Stack when User Input's Another Input.
        private void removeAllRows(DefaultTableModel tableModel) {
            int size = tableModel.getRowCount();
            for (int index = 0; index < size; index++) {
                tableModel.removeRow(0);
            }
        }

        // Displays the Rows Per Character of the User Input one after another character.
        private void insertRow(DefaultTableModel tableModel, String token,
                String action, String result, String stack) {
            Object[] object = new Object[4];
            object[0] = token;
            object[1] = action;
            object[2] = result;
            object[3] = stack;
            tableModel.addRow(object);
        }

    }

    public class InfixToPostfixModel {

        public DefaultTableModel tableModel;

        public String infixExpression, postfixExpression;

        // Creates The Column of the Jtable (Stack Table).
        public InfixToPostfixModel() {

            tableModel = new DefaultTableModel();
            tableModel.addColumn("Token");
            tableModel.addColumn("Action");
            tableModel.addColumn("Result");
            tableModel.addColumn("Stack");

        }

        // Getters of Methods.
        public DefaultTableModel getTableModel() {
            return tableModel;
        }

        public String getInfixExpression() {
            return infixExpression;
        }

        public void setInfixExpression(String infixExpression) {
            this.infixExpression = infixExpression;
        }

        public void setPostfixExpression(String postfixExpression) {
            this.postfixExpression = postfixExpression;
        }

        public String getPostfixExpression() {
            return postfixExpression;
        }
    }

    // Displays the Infix Expression on "Entered Infix Expression" on the GUI
    public String getInfixExpression() {
        return InfixInput1.getText().trim();

    }

    // Displays the Converted Infix to Postfix Notation on "Postfix Expression" on the GUI
    public void setPostfixExpression() {
        PostfixResult.setText(PostfixModel.getPostfixExpression());
    }

    /*/
            PART 2 : INFIX TO POSTFIX CONVERSION METHOD

            This part of the Code Converts Infix To Prefix Notation.
    
            NOTE : The code used in this the Prefix Conversion is similar to the Postfix Conversion. 
                    
                   Except that the String User Input(Infix Notation) will be Reversed during User Input.    
                   Prints the Reversed Infix Notation into the Stack Table, and the Postfix Output 
                   will once again be Reversed to get the Infix Notation's Prefix Notation Equivalent.
        
    /*/
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class PrefixConversionListener implements ActionListener {

        private final DataStructureGUI PrefixView;

        private final InfixToPrefixModel PrefixModel;

        public PrefixConversionListener(DataStructureGUI PrefixView, InfixToPrefixModel model) {
            this.PrefixView = PrefixView;
            this.PrefixModel = model;
        }

        @Override
        public void actionPerformed(ActionEvent event) {

            String expression2 = InfixInput2.getText();

            if (expression2.isEmpty()) {
                DisplayInput2.setText("NOTHING TO CONVERT!");
            } else {

                DisplayInput2.setText(expression2);
                String infixExpression2 = PrefixView.getInfixExpression2();
                PrefixModel.setInfixExpression2(infixExpression2);
                PrefixModel.setPrefixExpression(infixToPrefix(infixExpression2));
                PrefixView.setPrefixExpression();
            }
        }

        private String infixToPrefix(String expression) {

            removeAllRows(PrefixModel.getTableModel());
            String result = "";

            /*/ 
                REVERSE NUMBER 1 START
                This Method Reverses the Infix Notation before Outputing it into the Stack Table.   
            /*/
            char ch;
            String reverse = "";
            for (int i = 0; i < expression.length(); i++) {

                ch = expression.charAt(i);
                reverse = ch + reverse;
            }
            /// REVERSE NUMBER 1 END
            Stack<Character> stack = new Stack<>();

            for (int i = 0; i < reverse.length(); ++i) {

                char c = reverse.charAt(i);

                if (Character.isLetterOrDigit(c)) {

                    result += c;
                    updateResult(stack, c, result);

                } else if (c == '(') {

                    stack.push(c);
                    pushOperator(stack, c, result);

                } else if (c == ')') {

                    continue;

                } else {
                    // an operator is encountered
                    while (!stack.isEmpty() && precedence(c)
                            <= precedence(stack.peek())) {

                        result = popOperator(stack, result, true);
                    }
                    pushOperator(stack, c, result);
                }

            }
            // pop all the operators from the stack

            int size = stack.size();
            for (int index = 0; index < size; index++) {
                result = popOperator(stack, result, false);
            }

            return result;

        }

        private void updateResult(Stack<Character> stack, char c, String result) {
            String token = Character.toString(c);
            String action = "Add " + c + " to the result";
            insertRow(PrefixModel.getTableModel(), token, action, result,
                    displayStack(stack));
        }

        private void pushOperator(Stack<Character> stack, char c, String result) {
            stack.push(c);
            String token = Character.toString(c);
            String action = "Push " + c + " to stack";
            insertRow(PrefixModel.getTableModel(), token, action, result,
                    displayStack(stack));
        }

        private String popOperator(Stack<Character> stack, String result,
                boolean displayToken) {
            char o = stack.pop();
            String token = "";
            String action;

            if (displayToken) {
                token = Character.toString(o);
            }

            if (o == '(') {
                action = "Pop " + o + " from stack";
            } else {
                result += o;
                action = "Pop " + o + " from stack and add to result";
            }

            insertRow(PrefixModel.getTableModel(), token, action, result,
                    displayStack(stack));
            return result;
        }

        private int precedence(char o) {
            switch (o) {
                case '+', '-' -> {
                    return 1;
                }
                case '*', '/' -> {
                    return 2;
                }
                case '^' -> {
                    return 3;
                }
                case '(', ')' -> {
                    return 9;
                }
            }
            return -1;
        }

        private String displayStack(Stack<Character> stack) {
            String result = "";
            for (int index = (stack.size() - 1); index >= 0; index--) {
                result += stack.get(index);
            }
            return result;
        }

        private void removeAllRows(DefaultTableModel tableModel) {
            int size = tableModel.getRowCount();
            for (int index = 0; index < size; index++) {
                tableModel.removeRow(0);
            }
        }

        private void insertRow(DefaultTableModel tableModel, String token,
                String action, String result, String stack) {
            Object[] object = new Object[4];
            object[0] = token;
            object[1] = action;
            object[2] = result;
            object[3] = stack;
            tableModel.addRow(object);
        }

    }

    public class InfixToPrefixModel {

        public DefaultTableModel tableModel;

        public String infixExpression2, prefixExpression;

        public InfixToPrefixModel() {

            tableModel = new DefaultTableModel();
            tableModel.addColumn("Token");
            tableModel.addColumn("Action");
            tableModel.addColumn("Result");
            tableModel.addColumn("Stack");

        }

        public DefaultTableModel getTableModel() {
            return tableModel;
        }

        public String getInfixExpression2() {
            return infixExpression2;
        }

        public void setInfixExpression2(String infixExpression2) {
            this.infixExpression2 = infixExpression2;
        }

        public void setPrefixExpression(String prefixExpression) {
            this.prefixExpression = prefixExpression;
        }

        public String getPrefixExpression() {
            return prefixExpression;
        }
    }

    public String getInfixExpression2() {
        return InfixInput2.getText().trim();
    }

    // Prefix Output Declaration
    public void setPrefixExpression() {
        /*/ 
                REVERSE NUMBER 2
                This Method Reverses the Reversed Postfix Notation before Outputing it in the Prefix Notaion Output.   
        /*/
        char ch;
        String reverse = "";
        for (int i = 0; i < PrefixModel.getPrefixExpression().length(); i++) {

            ch = PrefixModel.getPrefixExpression().charAt(i);
            reverse = ch + reverse;
        }
        // REVERSE 2 END

        // Output Prefix Notation
        PrefixOutput.setText(reverse);

    }

    // GUI INTERFACE CONFIGURATION
    public DataStructureGUI() {

        PostfixModel = new InfixToPostfixModel();
        PrefixModel = new InfixToPrefixModel();

        initComponents();

        /*/
            This Block of Code Sets the GUI Interface Settings of The Program
        /*/
        // Dash Board of the Welcome Screen, Postfix Screen, Prefix Screen, and About Screen.
        WelcomeDash.setVisible(true);
        PostfixDash.setVisible(false);
        AboutDash.setVisible(false);
        PrefixDash.setVisible(false);

        // CENTER POPUP WINDOW
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);

        // COMPANY LOGO in the TITLE BAR
        ImageIcon CompLogo = new ImageIcon(getClass().getClassLoader().getResource("res/Brion-Tactical-Systems.png"));
        Image GetLogo = CompLogo.getImage();
        Image LogoPost = GetLogo.getScaledInstance(CompanyLogo.getWidth(), CompanyLogo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon ScaledLogo = new ImageIcon(LogoPost);
        CompanyLogo.setIcon(ScaledLogo);

        ImageIcon LogShadow = new ImageIcon(getClass().getClassLoader().getResource("res/BTSLogoShad.png"));
        Image GetShadow = LogShadow.getImage();
        Image ShadowPost = GetShadow.getScaledInstance(LogoShadow.getWidth(), LogoShadow.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon ScaledShad = new ImageIcon(ShadowPost);
        LogoShadow.setIcon(ScaledShad);

        ImageIcon CompLogo2 = new ImageIcon(getClass().getClassLoader().getResource("res/Brion-Tactical-Systems.png"));
        Image GetLogo2 = CompLogo2.getImage();
        Image LogoPost2 = GetLogo2.getScaledInstance(AboutLogo.getWidth(), AboutLogo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon ScaledLogo2 = new ImageIcon(LogoPost2);
        AboutLogo.setIcon(ScaledLogo2);

        ImageIcon LogShadow2 = new ImageIcon(getClass().getClassLoader().getResource("res/BTSLogoShad.png"));
        Image GetShadow2 = LogShadow2.getImage();
        Image ShadowPost2 = GetShadow2.getScaledInstance(AboutLogoShad.getWidth(), AboutLogoShad.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon ScaledShad2 = new ImageIcon(ShadowPost2);
        AboutLogoShad.setIcon(ScaledShad2);

        ImageIcon MyPic = new ImageIcon(getClass().getClassLoader().getResource("res/MyPicture.jpg"));
        Image GetPic = MyPic.getImage();
        Image SetPic = GetPic.getScaledInstance(MyPicture.getWidth(), MyPicture.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon ScaledPic = new ImageIcon(SetPic);
        MyPicture.setIcon(ScaledPic);

        /*/
            Declares the Action Listener of the Enter Buttons in the GUI.
        /*/
        // ENTER BUTTON FUNCTION
        EnterButton1.addActionListener(new PostfixConversionListener(this, PostfixModel));
        EnterButton2.addActionListener(new PrefixConversionListener(this, PrefixModel));
        
        /*/
            Sets the Display Settings (Layout) of the JTable (Stack Table) for both Postfix and Prefix.
        /*/
        // DISPLAY JTABLE STACK FOR PREFIX
        TableCellRenderer HeaderRendererPostfix = PostfixTable.getTableHeader().getDefaultRenderer();
        JLabel PostfixHeaderLabel = (JLabel) HeaderRendererPostfix;
        PostfixHeaderLabel.setHorizontalAlignment(JLabel.CENTER);

        PostfixTable.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        PostfixTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        PostfixTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        PostfixTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        PostfixTable.getColumnModel().getColumn(3).setPreferredWidth(50);

        DefaultTableCellRenderer PostfixCellRenderer = new DefaultTableCellRenderer();
        PostfixCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        PostfixTable.getColumnModel().getColumn(0).setCellRenderer(PostfixCellRenderer);

        PostfixCellRenderer = new DefaultTableCellRenderer();
        PostfixCellRenderer.setHorizontalAlignment(JLabel.TRAILING);
        PostfixTable.getColumnModel().getColumn(2).setCellRenderer(PostfixCellRenderer);
        PostfixTable.getColumnModel().getColumn(3).setCellRenderer(PostfixCellRenderer);

        // DISPLAY JTABLE STACK FOR PREFIX
        TableCellRenderer HeaderRendererPrefix = PrefixTable.getTableHeader().getDefaultRenderer();
        JLabel HeaderLabelPrefix = (JLabel) HeaderRendererPrefix;
        HeaderLabelPrefix.setHorizontalAlignment(JLabel.CENTER);

        PrefixTable.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        PrefixTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        PrefixTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        PrefixTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        PrefixTable.getColumnModel().getColumn(3).setPreferredWidth(50);

        DefaultTableCellRenderer PrefixCellRenderer = new DefaultTableCellRenderer();
        PrefixCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        PrefixTable.getColumnModel().getColumn(0).setCellRenderer(PrefixCellRenderer);

        PrefixCellRenderer = new DefaultTableCellRenderer();
        PrefixCellRenderer.setHorizontalAlignment(JLabel.TRAILING);
        PrefixTable.getColumnModel().getColumn(2).setCellRenderer(PrefixCellRenderer);
        PrefixTable.getColumnModel().getColumn(3).setCellRenderer(PrefixCellRenderer);
        
        sound.setURL(startup);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TitleBar = new javax.swing.JPanel();
        BTSTitle = new javax.swing.JLabel();
        BTSShadow = new javax.swing.JLabel();
        StackTitle = new javax.swing.JLabel();
        StackShadow = new javax.swing.JLabel();
        CompanyLogo = new javax.swing.JLabel();
        LogoShadow = new javax.swing.JLabel();
        Exit = new javax.swing.JButton();
        Minimize = new javax.swing.JButton();
        NavigationBar = new javax.swing.JPanel();
        WelcomeNav = new javax.swing.JPanel();
        WelcomeTitle = new javax.swing.JLabel();
        PostfixNav = new javax.swing.JPanel();
        PostfixTitle = new javax.swing.JLabel();
        AboutNav = new javax.swing.JPanel();
        AboutTitle = new javax.swing.JLabel();
        WelcomeIndicator = new javax.swing.JPanel();
        PostfixIndicator = new javax.swing.JPanel();
        AboutIndicator = new javax.swing.JPanel();
        PrefixNav = new javax.swing.JPanel();
        PrefixTitle = new javax.swing.JLabel();
        PrefixIndicator = new javax.swing.JPanel();
        NavigationTitle = new javax.swing.JPanel();
        NavigationLabel = new javax.swing.JLabel();
        WelcomeDash = new javax.swing.JPanel();
        WelcomeDashTitle = new javax.swing.JLabel();
        WelcomeLine = new javax.swing.JLabel();
        WelcomeLine1 = new javax.swing.JLabel();
        WelcomeLine2 = new javax.swing.JLabel();
        WelcomeLine3 = new javax.swing.JLabel();
        WelcomeLine4 = new javax.swing.JLabel();
        WelcomeLine5 = new javax.swing.JLabel();
        WelcomeLine6 = new javax.swing.JLabel();
        WelcomeLine7 = new javax.swing.JLabel();
        WelcomeLine8 = new javax.swing.JLabel();
        PostfixDash = new javax.swing.JPanel();
        PostFixDashTitle = new javax.swing.JLabel();
        InfixInput1 = new javax.swing.JTextField();
        InfixLabel1 = new javax.swing.JLabel();
        PostfixLabel = new javax.swing.JLabel();
        EnterButton1 = new javax.swing.JButton();
        DisplayInput1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Reset1 = new javax.swing.JButton();
        PostfixScrollPanel = new javax.swing.JScrollPane();
        PostfixTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        PostfixResult = new javax.swing.JLabel();
        PrefixDash = new javax.swing.JPanel();
        PreFixDashTitle = new javax.swing.JLabel();
        InfixLabel2 = new javax.swing.JLabel();
        InfixInput2 = new javax.swing.JTextField();
        EnterButton2 = new javax.swing.JButton();
        Reset2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        PostfixLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        PrefixScrollPanel = new javax.swing.JScrollPane();
        PrefixTable = new javax.swing.JTable();
        PrefixOutput = new javax.swing.JLabel();
        DisplayInput2 = new javax.swing.JLabel();
        AboutDash = new javax.swing.JPanel();
        AboutLogo = new javax.swing.JLabel();
        AboutLogoShad = new javax.swing.JLabel();
        BTSTitle1 = new javax.swing.JLabel();
        BTSShadow1 = new javax.swing.JLabel();
        StackTitle1 = new javax.swing.JLabel();
        StackShadow1 = new javax.swing.JLabel();
        Line1 = new javax.swing.JLabel();
        Line2 = new javax.swing.JLabel();
        Line3 = new javax.swing.JLabel();
        Line4 = new javax.swing.JLabel();
        Line5 = new javax.swing.JLabel();
        Line6 = new javax.swing.JLabel();
        Line7 = new javax.swing.JLabel();
        Line8 = new javax.swing.JLabel();
        Line9 = new javax.swing.JLabel();
        MyPicture = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TitleBar.setBackground(new java.awt.Color(0, 0, 51));
        TitleBar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 208, 0), 1, true));
        TitleBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                TitleBarMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                TitleBarMouseMoved(evt);
            }
        });
        TitleBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        try{
            IronShark = Font.createFont(Font.TRUETYPE_FONT, new File("src\\res\\Iron-Shark.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File("src\\res\\Iron-Shark.ttf")));

        }catch(IOException | FontFormatException e){

        }
        BTSTitle.setText("BRION TACTICAL SYSTEMS");
        BTSTitle.setFont(IronShark);
        BTSTitle.setForeground(new Color(255,208,0));
        TitleBar.add(BTSTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 500, 40));

        try{
            IronShark = Font.createFont(Font.TRUETYPE_FONT, new File("src\\res\\Iron-Shark.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File("src\\res\\Iron-Shark.ttf")));

        }catch(IOException | FontFormatException e){

        }
        BTSShadow.setBackground(new java.awt.Color(0, 0, 0));
        BTSShadow.setText("BRION TACTICAL SYSTEMS");
        BTSShadow.setFont(IronShark);
        BTSShadow.setForeground(new Color(0,0,0));
        TitleBar.add(BTSShadow, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 45, 500, 40));

        StackTitle.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        StackTitle.setForeground(new java.awt.Color(255, 255, 255));
        StackTitle.setText("STACK IMPLEMENTATION ");
        TitleBar.add(StackTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 370, 40));

        StackShadow.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        StackShadow.setForeground(new java.awt.Color(0, 0, 0));
        StackShadow.setText("STACK IMPLEMENTATION ");
        TitleBar.add(StackShadow, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 75, -1, 40));

        CompanyLogo.setText("LOGO");
        TitleBar.add(CompanyLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 5, 160, 160));

        LogoShadow.setText("LOGO");
        TitleBar.add(LogoShadow, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 160, 160));

        Exit.setBackground(new java.awt.Color(0, 0, 51));
        Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/DehoveredExit.png"))); // NOI18N
        Exit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Exit.setBorderPainted(false);
        Exit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Exit.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/ClickedExit.png"))); // NOI18N
        Exit.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/res/HoveredExit.png"))); // NOI18N
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });
        TitleBar.add(Exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 10, 30, 30));

        Minimize.setBackground(new java.awt.Color(0, 0, 51));
        Minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/DehoveredMinimize.png"))); // NOI18N
        Minimize.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Minimize.setBorderPainted(false);
        Minimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Minimize.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/res/Clicked Minimize.png"))); // NOI18N
        Minimize.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/res/HoveredMinimize.png"))); // NOI18N
        Minimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MinimizeActionPerformed(evt);
            }
        });
        TitleBar.add(Minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(865, 10, 30, 30));

        getContentPane().add(TitleBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 130));

        NavigationBar.setBackground(new java.awt.Color(0, 0, 51));
        NavigationBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 208, 0)));
        NavigationBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        WelcomeNav.setBackground(new java.awt.Color(51, 51, 51));
        WelcomeNav.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        WelcomeTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        WelcomeTitle.setForeground(new java.awt.Color(255, 255, 255));
        WelcomeTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WelcomeTitle.setText("Welcome");
        WelcomeTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                WelcomeTitleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                WelcomeTitleMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                WelcomeTitleMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                WelcomeTitleMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout WelcomeNavLayout = new javax.swing.GroupLayout(WelcomeNav);
        WelcomeNav.setLayout(WelcomeNavLayout);
        WelcomeNavLayout.setHorizontalGroup(
            WelcomeNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(WelcomeTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
        );
        WelcomeNavLayout.setVerticalGroup(
            WelcomeNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(WelcomeTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        NavigationBar.add(WelcomeNav, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 80, 180, 50));

        PostfixNav.setBackground(new java.awt.Color(51, 51, 51));
        PostfixNav.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        PostfixTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        PostfixTitle.setForeground(new java.awt.Color(255, 255, 255));
        PostfixTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PostfixTitle.setText("Infix To Postfix");
        PostfixTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PostfixTitleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PostfixTitleMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                PostfixTitleMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                PostfixTitleMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout PostfixNavLayout = new javax.swing.GroupLayout(PostfixNav);
        PostfixNav.setLayout(PostfixNavLayout);
        PostfixNavLayout.setHorizontalGroup(
            PostfixNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PostfixTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
        );
        PostfixNavLayout.setVerticalGroup(
            PostfixNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PostfixTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        NavigationBar.add(PostfixNav, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 140, 180, 50));

        AboutNav.setBackground(new java.awt.Color(51, 51, 51));
        AboutNav.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        AboutTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        AboutTitle.setForeground(new java.awt.Color(255, 255, 255));
        AboutTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AboutTitle.setText("About");
        AboutTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AboutTitleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AboutTitleMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                AboutTitleMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                AboutTitleMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout AboutNavLayout = new javax.swing.GroupLayout(AboutNav);
        AboutNav.setLayout(AboutNavLayout);
        AboutNavLayout.setHorizontalGroup(
            AboutNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AboutTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
        );
        AboutNavLayout.setVerticalGroup(
            AboutNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AboutTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        NavigationBar.add(AboutNav, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 260, 180, 50));

        WelcomeIndicator.setBackground(new java.awt.Color(255, 208, 0));
        WelcomeIndicator.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout WelcomeIndicatorLayout = new javax.swing.GroupLayout(WelcomeIndicator);
        WelcomeIndicator.setLayout(WelcomeIndicatorLayout);
        WelcomeIndicatorLayout.setHorizontalGroup(
            WelcomeIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        WelcomeIndicatorLayout.setVerticalGroup(
            WelcomeIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        NavigationBar.add(WelcomeIndicator, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 10, 50));

        PostfixIndicator.setBackground(new java.awt.Color(0, 0, 51));
        PostfixIndicator.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout PostfixIndicatorLayout = new javax.swing.GroupLayout(PostfixIndicator);
        PostfixIndicator.setLayout(PostfixIndicatorLayout);
        PostfixIndicatorLayout.setHorizontalGroup(
            PostfixIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        PostfixIndicatorLayout.setVerticalGroup(
            PostfixIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        NavigationBar.add(PostfixIndicator, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 10, 50));

        AboutIndicator.setBackground(new java.awt.Color(0, 0, 51));
        AboutIndicator.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout AboutIndicatorLayout = new javax.swing.GroupLayout(AboutIndicator);
        AboutIndicator.setLayout(AboutIndicatorLayout);
        AboutIndicatorLayout.setHorizontalGroup(
            AboutIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        AboutIndicatorLayout.setVerticalGroup(
            AboutIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        NavigationBar.add(AboutIndicator, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 260, 10, 50));

        PrefixNav.setBackground(new java.awt.Color(51, 51, 51));
        PrefixNav.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        PrefixTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        PrefixTitle.setForeground(new java.awt.Color(255, 255, 255));
        PrefixTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PrefixTitle.setText("Infix To Prefix");
        PrefixTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PrefixTitleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PrefixTitleMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                PrefixTitleMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                PrefixTitleMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout PrefixNavLayout = new javax.swing.GroupLayout(PrefixNav);
        PrefixNav.setLayout(PrefixNavLayout);
        PrefixNavLayout.setHorizontalGroup(
            PrefixNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PrefixTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
        );
        PrefixNavLayout.setVerticalGroup(
            PrefixNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PrefixTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        NavigationBar.add(PrefixNav, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 200, 180, 50));

        PrefixIndicator.setBackground(new java.awt.Color(0, 0, 51));
        PrefixIndicator.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout PrefixIndicatorLayout = new javax.swing.GroupLayout(PrefixIndicator);
        PrefixIndicator.setLayout(PrefixIndicatorLayout);
        PrefixIndicatorLayout.setHorizontalGroup(
            PrefixIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        PrefixIndicatorLayout.setVerticalGroup(
            PrefixIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        NavigationBar.add(PrefixIndicator, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 10, 50));

        NavigationTitle.setBackground(new java.awt.Color(255, 208, 0));
        NavigationTitle.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 51), new java.awt.Color(0, 0, 51)));
        NavigationTitle.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        NavigationLabel.setBackground(new java.awt.Color(255, 208, 0));
        NavigationLabel.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        NavigationLabel.setForeground(new java.awt.Color(0, 0, 0));
        NavigationLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NavigationLabel.setText("==== MENU ====");
        NavigationLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        NavigationTitle.add(NavigationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 50));

        NavigationBar.add(NavigationTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 20, 180, 50));

        getContentPane().add(NavigationBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 129, 191, 498));

        WelcomeDash.setBackground(new java.awt.Color(20, 20, 20));
        WelcomeDash.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 208, 0), 1, true));
        WelcomeDash.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        WelcomeDashTitle.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        WelcomeDashTitle.setForeground(new java.awt.Color(255, 255, 255));
        WelcomeDashTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WelcomeDashTitle.setText("WELCOME");
        WelcomeDash.add(WelcomeDashTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 520, 70));

        WelcomeLine.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        WelcomeLine.setForeground(new java.awt.Color(255, 255, 255));
        WelcomeLine.setText("This Program is used to convert Infix to Postfix, as well as Infix to Prefix");
        WelcomeDash.add(WelcomeLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 510, -1));

        WelcomeLine1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        WelcomeLine1.setForeground(new java.awt.Color(255, 255, 255));
        WelcomeLine1.setText("Notations. ");
        WelcomeDash.add(WelcomeLine1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 510, -1));

        WelcomeLine2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        WelcomeLine2.setForeground(new java.awt.Color(255, 255, 255));
        WelcomeLine2.setText("This Progam also simulates the Expression Parsing of the Infix Notations,");
        WelcomeDash.add(WelcomeLine2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 510, -1));

        WelcomeLine3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        WelcomeLine3.setForeground(new java.awt.Color(255, 255, 255));
        WelcomeLine3.setText("where the Infix Notation is being evaluated per character of operators ");
        WelcomeDash.add(WelcomeLine3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 267, 510, -1));

        WelcomeLine4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        WelcomeLine4.setForeground(new java.awt.Color(255, 255, 255));
        WelcomeLine4.setText("and Operands that is being displayed in the Stack Table whenever the");
        WelcomeDash.add(WelcomeLine4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 284, 510, -1));

        WelcomeLine5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        WelcomeLine5.setForeground(new java.awt.Color(255, 255, 255));
        WelcomeLine5.setText("conversion of Infix to Postfix and Infix to Prefix Notations takes place.");
        WelcomeDash.add(WelcomeLine5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 301, 510, -1));

        WelcomeLine6.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        WelcomeLine6.setForeground(new java.awt.Color(255, 255, 255));
        WelcomeLine6.setText("\"Imagination is more important than knowledge. For Knowledge is Limited,");
        WelcomeDash.add(WelcomeLine6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 335, 530, -1));

        WelcomeLine7.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        WelcomeLine7.setForeground(new java.awt.Color(255, 255, 255));
        WelcomeLine7.setText("whereas imagination embraces the entire world, stimulating progress,");
        WelcomeDash.add(WelcomeLine7, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 352, 510, -1));

        WelcomeLine8.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        WelcomeLine8.setForeground(new java.awt.Color(255, 255, 255));
        WelcomeLine8.setText("giving birth to evolution\" - Albert Einstein");
        WelcomeDash.add(WelcomeLine8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 369, 510, -1));

        getContentPane().add(WelcomeDash, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 128, 750, 500));

        PostfixDash.setBackground(new java.awt.Color(20, 20, 20));
        PostfixDash.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 208, 0), 1, true));
        PostfixDash.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PostFixDashTitle.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        PostFixDashTitle.setForeground(new java.awt.Color(255, 255, 255));
        PostFixDashTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PostFixDashTitle.setText("INFIX TO POSTFIX NOTATION ");
        PostfixDash.add(PostFixDashTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 410, 40));

        InfixInput1.setBackground(new java.awt.Color(255, 255, 255));
        InfixInput1.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        InfixInput1.setForeground(new java.awt.Color(0, 0, 0));
        InfixInput1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        InfixInput1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        InfixInput1.setSelectionColor(new java.awt.Color(255, 208, 0));
        PostfixDash.add(InfixInput1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 280, 40));

        InfixLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        InfixLabel1.setForeground(new java.awt.Color(255, 255, 255));
        InfixLabel1.setText("INFIX EXPRESSION       :");
        PostfixDash.add(InfixLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 190, 40));

        PostfixLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        PostfixLabel.setForeground(new java.awt.Color(255, 255, 255));
        PostfixLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PostfixLabel.setText("POSTFIX EXPRESSION ");
        PostfixDash.add(PostfixLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 130, 350, 40));

        EnterButton1.setBackground(new java.awt.Color(255, 208, 0));
        EnterButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        EnterButton1.setForeground(new java.awt.Color(0, 0, 0));
        EnterButton1.setText("ENTER");
        PostfixDash.add(EnterButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, 100, 40));

        DisplayInput1.setBackground(new java.awt.Color(51, 51, 51));
        DisplayInput1.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        DisplayInput1.setForeground(new java.awt.Color(255, 255, 255));
        DisplayInput1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DisplayInput1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PostfixDash.add(DisplayInput1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 340, 40));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ENTERED INFIX EXPRESSION   ");
        PostfixDash.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 350, 40));

        Reset1.setBackground(new java.awt.Color(153, 0, 0));
        Reset1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Reset1.setForeground(new java.awt.Color(255, 255, 255));
        Reset1.setText("RESET");
        Reset1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Reset1ActionPerformed(evt);
            }
        });
        PostfixDash.add(Reset1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 70, 100, 40));

        PostfixScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        PostfixScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        PostfixScrollPanel.setViewportBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PostfixScrollPanel.setAutoscrolls(true);

        PostfixTable = new javax.swing.JTable(PostfixModel.getTableModel());
        PostfixTable.setBackground(new java.awt.Color(255, 255, 255));
        PostfixTable.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        PostfixTable.setForeground(new java.awt.Color(0, 0, 0));
        PostfixTable.setColumnSelectionAllowed(true);
        PostfixTable.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        PostfixTable.setGridColor(new java.awt.Color(0, 0, 0));
        PostfixTable.setSelectionBackground(new java.awt.Color(0, 0, 51));
        PostfixTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        PostfixTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        PostfixTable.setShowGrid(true);
        PostfixTable.getTableHeader().setReorderingAllowed(false);
        PostfixScrollPanel.setViewportView(PostfixTable);
        PostfixTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        PostfixDash.add(PostfixScrollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 730, 220));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("STACK TABLE");
        PostfixDash.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 730, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 208, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("=======================================================================");
        PostfixDash.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 730, -1));

        PostfixResult.setBackground(new java.awt.Color(51, 51, 51));
        PostfixResult.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        PostfixResult.setForeground(new java.awt.Color(255, 255, 255));
        PostfixResult.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PostfixResult.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PostfixDash.add(PostfixResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, 360, 40));

        getContentPane().add(PostfixDash, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 128, 750, 500));

        PrefixDash.setBackground(new java.awt.Color(20, 20, 20));
        PrefixDash.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 208, 0), 1, true));
        PrefixDash.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PreFixDashTitle.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        PreFixDashTitle.setForeground(new java.awt.Color(255, 255, 255));
        PreFixDashTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PreFixDashTitle.setText("INFIX TO PREFIX NOTATION ");
        PrefixDash.add(PreFixDashTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 410, 40));

        InfixLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        InfixLabel2.setForeground(new java.awt.Color(255, 255, 255));
        InfixLabel2.setText("INFIX EXPRESSION       :");
        PrefixDash.add(InfixLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 190, 40));

        InfixInput2.setBackground(new java.awt.Color(255, 255, 255));
        InfixInput2.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        InfixInput2.setForeground(new java.awt.Color(0, 0, 0));
        InfixInput2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        InfixInput2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        InfixInput2.setSelectionColor(new java.awt.Color(255, 208, 0));
        PrefixDash.add(InfixInput2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 280, 40));

        EnterButton2.setBackground(new java.awt.Color(255, 208, 0));
        EnterButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        EnterButton2.setForeground(new java.awt.Color(0, 0, 0));
        EnterButton2.setText("ENTER");
        PrefixDash.add(EnterButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, 100, 40));

        Reset2.setBackground(new java.awt.Color(153, 0, 0));
        Reset2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Reset2.setForeground(new java.awt.Color(255, 255, 255));
        Reset2.setText("RESET");
        Reset2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Reset2ActionPerformed(evt);
            }
        });
        PrefixDash.add(Reset2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 70, 100, 40));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ENTERED INFIX EXPRESSION   ");
        PrefixDash.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 350, 40));

        PostfixLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        PostfixLabel1.setForeground(new java.awt.Color(255, 255, 255));
        PostfixLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PostfixLabel1.setText("PREFIX EXPRESSION");
        PrefixDash.add(PostfixLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 130, 350, 40));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 208, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("=======================================================================");
        PrefixDash.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 730, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("STACK TABLE");
        PrefixDash.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 730, 40));

        PrefixScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        PrefixScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        PrefixScrollPanel.setViewportBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PrefixScrollPanel.setAutoscrolls(true);

        PrefixTable = new javax.swing.JTable(PrefixModel.getTableModel());
        PrefixTable.setBackground(new java.awt.Color(255, 255, 255));
        PrefixTable.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        PrefixTable.setForeground(new java.awt.Color(0, 0, 0));
        PrefixTable.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        PrefixTable.setGridColor(new java.awt.Color(0, 0, 0));
        PrefixTable.setSelectionBackground(new java.awt.Color(0, 0, 51));
        PrefixTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        PrefixTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        PrefixTable.setShowGrid(true);
        PrefixTable.getTableHeader().setReorderingAllowed(false);
        PrefixScrollPanel.setViewportView(PrefixTable);
        PrefixTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        PrefixDash.add(PrefixScrollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 730, 220));

        PrefixOutput.setBackground(new java.awt.Color(51, 51, 51));
        PrefixOutput.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        PrefixOutput.setForeground(new java.awt.Color(255, 255, 255));
        PrefixOutput.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PrefixOutput.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PrefixDash.add(PrefixOutput, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, 360, 40));

        DisplayInput2.setBackground(new java.awt.Color(51, 51, 51));
        DisplayInput2.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        DisplayInput2.setForeground(new java.awt.Color(255, 255, 255));
        DisplayInput2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DisplayInput2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PrefixDash.add(DisplayInput2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 340, 40));

        getContentPane().add(PrefixDash, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 128, 750, 500));

        AboutDash.setBackground(new java.awt.Color(20, 20, 20));
        AboutDash.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 208, 0), 1, true));
        AboutDash.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AboutLogo.setText("LOGO");
        AboutDash.add(AboutLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 300, 300));

        AboutLogoShad.setText("LOGO");
        AboutDash.add(AboutLogoShad, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 300, 300));

        try{
            IronShark = Font.createFont(Font.TRUETYPE_FONT, new File("src\\res\\Iron-Shark.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File("src\\res\\Iron-Shark.ttf")));

        }catch(IOException | FontFormatException e){

        }
        BTSTitle1.setText("BRION TACTICAL SYSTEMS");
        BTSTitle1.setFont(IronShark);
        BTSTitle1.setForeground(new Color(255,208,0));
        BTSTitle1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                BTSTitle1PropertyChange(evt);
            }
        });
        AboutDash.add(BTSTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 500, 40));

        try{
            IronShark = Font.createFont(Font.TRUETYPE_FONT, new File("src\\res\\Iron-Shark.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File("src\\res\\Iron-Shark.ttf")));

        }catch(IOException | FontFormatException e){

        }
        BTSShadow1.setBackground(new java.awt.Color(0, 0, 0));
        BTSShadow1.setText("BRION TACTICAL SYSTEMS");
        BTSShadow1.setFont(IronShark);
        BTSShadow1.setForeground(new Color(0,0,0));
        AboutDash.add(BTSShadow1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 35, 500, 40));

        StackTitle1.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        StackTitle1.setForeground(new java.awt.Color(255, 255, 255));
        StackTitle1.setText("STACK IMPLEMENTATION ");
        AboutDash.add(StackTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 370, 40));

        StackShadow1.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        StackShadow1.setForeground(new java.awt.Color(0, 0, 0));
        StackShadow1.setText("STACK IMPLEMENTATION ");
        AboutDash.add(StackShadow1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 65, -1, 40));

        Line1.setBackground(new java.awt.Color(51, 51, 51));
        Line1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Line1.setForeground(new java.awt.Color(255, 255, 255));
        Line1.setText("This program is for educational purposes only! Do not Distribute.");
        AboutDash.add(Line1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 410, -1));

        Line2.setBackground(new java.awt.Color(51, 51, 51));
        Line2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Line2.setForeground(new java.awt.Color(255, 255, 255));
        Line2.setText("This Project was made as a Finals Case Study Requirement for ");
        AboutDash.add(Line2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 450, -1));

        Line3.setBackground(new java.awt.Color(51, 51, 51));
        Line3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Line3.setForeground(new java.awt.Color(255, 255, 255));
        Line3.setText("the CC4, Data Structures and Algorithm Subject .");
        AboutDash.add(Line3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 150, 390, -1));

        Line4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Line4.setForeground(new java.awt.Color(255, 255, 255));
        Line4.setText("Developer : Jhon Brix G. Brion");
        AboutDash.add(Line4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 270, 400, 30));

        Line5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Line5.setForeground(new java.awt.Color(255, 255, 255));
        Line5.setText("Year and Section : 2nd Year, BSCS 2B");
        AboutDash.add(Line5, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 300, 400, 30));

        Line6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Line6.setForeground(new java.awt.Color(255, 255, 255));
        Line6.setText("Development Date : November 10 2021 to December 9 2021");
        AboutDash.add(Line6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 330, 420, 30));

        Line7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Line7.setForeground(new java.awt.Color(255, 255, 255));
        Line7.setText("Submission Date : December 10 2021");
        AboutDash.add(Line7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 360, 420, 30));

        Line8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Line8.setForeground(new java.awt.Color(255, 255, 255));
        Line8.setText("Subject : CC4 - Data Structures and Algorithm  ");
        AboutDash.add(Line8, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 390, 420, 30));

        Line9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Line9.setForeground(new java.awt.Color(255, 255, 255));
        Line9.setText("Professor : Jo Anne G. Cura - JAGC");
        AboutDash.add(Line9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 420, 420, 30));

        MyPicture.setText("MyPicture");
        MyPicture.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 208, 0)));
        MyPicture.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        AboutDash.add(MyPicture, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 290, 260));

        jLabel7.setForeground(new java.awt.Color(255, 208, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("==========================================================================================");
        AboutDash.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 730, -1));

        getContentPane().add(AboutDash, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 128, 750, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*/
        This Part Set the Settings of the Navigation Buttons in the GUI.
    /*/
    // NAVIGATION BUTTONS 1.1
    // Hovered in Navigation Button
    public void setColor(JPanel hovered) {

        hovered.setBackground(new Color(110, 110, 110));

    }

    // De-Hovered in Navigation Button
    public void resetColor(JPanel deHovered) {

        deHovered.setBackground(new Color(51, 51, 51));

    }


    private void WelcomeTitleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_WelcomeTitleMouseEntered
        setColor(WelcomeNav);
    }//GEN-LAST:event_WelcomeTitleMouseEntered

    private void WelcomeTitleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_WelcomeTitleMouseExited
        resetColor(WelcomeNav);
    }//GEN-LAST:event_WelcomeTitleMouseExited

    private void PostfixTitleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PostfixTitleMouseEntered
        setColor(PostfixNav);
    }//GEN-LAST:event_PostfixTitleMouseEntered

    private void PostfixTitleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PostfixTitleMouseExited
        resetColor(PostfixNav);
    }//GEN-LAST:event_PostfixTitleMouseExited

    private void AboutTitleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AboutTitleMouseEntered
        setColor(AboutNav);
    }//GEN-LAST:event_AboutTitleMouseEntered

    private void AboutTitleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AboutTitleMouseExited
        resetColor(AboutNav);
    }//GEN-LAST:event_AboutTitleMouseExited

    // NAVIGATION BUTTONS 1.2
    // Pressed & Released Navigation Buttons
    // WelcomeNav

    private void WelcomeTitleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_WelcomeTitleMousePressed
        WelcomeDash.setVisible(true);
        PostfixDash.setVisible(false);
        PrefixDash.setVisible(false);
        AboutDash.setVisible(false);

        WelcomeNav.setBackground(new Color(255, 255, 255));
        WelcomeTitle.setForeground(new Color(0, 0, 0));

        PostfixNav.setBackground(new Color(51, 51, 51));
        PrefixNav.setBackground(new Color(51, 51, 51));
        AboutNav.setBackground(new Color(51, 51, 51));

        WelcomeIndicator.setBackground(new Color(255, 208, 0));
        PostfixIndicator.setBackground(new Color(0, 0, 51));
        PrefixIndicator.setBackground(new Color(0, 0, 51));
        AboutIndicator.setBackground(new Color(0, 0, 51));
    }//GEN-LAST:event_WelcomeTitleMousePressed

    private void WelcomeTitleMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_WelcomeTitleMouseReleased
        WelcomeDash.setVisible(true);
        PostfixDash.setVisible(false);
        PrefixDash.setVisible(false);
        AboutDash.setVisible(false);

        WelcomeNav.setBackground(new Color(51, 51, 51));
        WelcomeTitle.setForeground(new Color(255, 255, 255));

        PostfixNav.setBackground(new Color(51, 51, 51));
        PrefixNav.setBackground(new Color(51, 51, 51));
        AboutNav.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_WelcomeTitleMouseReleased

    //PostfixNav

    private void PostfixTitleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PostfixTitleMousePressed
        WelcomeDash.setVisible(false);
        PostfixDash.setVisible(true);
        PrefixDash.setVisible(false);
        AboutDash.setVisible(false);

        PostfixNav.setBackground(new Color(255, 255, 255));
        PostfixTitle.setForeground(new Color(0, 0, 0));

        WelcomeNav.setBackground(new Color(51, 51, 51));
        PrefixNav.setBackground(new Color(51, 51, 51));
        AboutNav.setBackground(new Color(51, 51, 51));

        WelcomeIndicator.setBackground(new Color(0, 0, 51));
        PostfixIndicator.setBackground(new Color(255, 208, 0));
        PrefixIndicator.setBackground(new Color(0, 0, 51));
        AboutIndicator.setBackground(new Color(0, 0, 51));
    }//GEN-LAST:event_PostfixTitleMousePressed

    private void PostfixTitleMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PostfixTitleMouseReleased
        WelcomeDash.setVisible(false);
        PostfixDash.setVisible(true);
        PrefixDash.setVisible(false);
        AboutDash.setVisible(false);

        PostfixNav.setBackground(new Color(51, 51, 51));
        PostfixTitle.setForeground(new Color(255, 255, 255));

        WelcomeNav.setBackground(new Color(51, 51, 51));
        PrefixNav.setBackground(new Color(51, 51, 51));
        AboutNav.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_PostfixTitleMouseReleased

    //AboutNav
    private void AboutTitleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AboutTitleMousePressed
        WelcomeDash.setVisible(false);
        PostfixDash.setVisible(false);
        PrefixDash.setVisible(false);
        AboutDash.setVisible(true);

        AboutNav.setBackground(new Color(255, 255, 255));
        AboutTitle.setForeground(new Color(0, 0, 0));

        WelcomeNav.setBackground(new Color(51, 51, 51));
        PrefixNav.setBackground(new Color(51, 51, 51));
        PostfixNav.setBackground(new Color(51, 51, 51));

        WelcomeIndicator.setBackground(new Color(0, 0, 51));
        PostfixIndicator.setBackground(new Color(0, 0, 51));
        PrefixIndicator.setBackground(new Color(0, 0, 51));
        AboutIndicator.setBackground(new Color(255, 208, 0));
    }//GEN-LAST:event_AboutTitleMousePressed

    private void AboutTitleMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AboutTitleMouseReleased
        WelcomeDash.setVisible(false);
        PostfixDash.setVisible(false);
        PrefixDash.setVisible(false);
        AboutDash.setVisible(true);

        AboutNav.setBackground(new Color(51, 51, 51));
        AboutTitle.setForeground(new Color(255, 255, 255));

        WelcomeNav.setBackground(new Color(51, 51, 51));
        PrefixNav.setBackground(new Color(51, 51, 51));
        PostfixNav.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_AboutTitleMouseReleased
    /*/
        The Ability of the Window to be dragged from the Title Bar. 
    /*/

    // WINDOW DRAGGER 
    int xMouse;
    int yMouse;

    private void TitleBarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TitleBarMouseDragged
        // WINDOW DRAGGER
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_TitleBarMouseDragged

    private void TitleBarMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TitleBarMouseMoved
        // JFRAME DRAGGER PART 2
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_TitleBarMouseMoved

    // EXIT BUTTON funtionality
    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_ExitActionPerformed

    // MINIMIZE BUTTON functionality
    private void MinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinimizeActionPerformed
        setState(DataStructureGUI.ICONIFIED);
    }//GEN-LAST:event_MinimizeActionPerformed

    private void Reset1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Reset1ActionPerformed

        // RESET BUTTON FUNCTION of POSTFIX GUI
        InfixInput1.setText("");
        DisplayInput1.setText("");
        PostfixResult.setText("");
        DefaultTableModel reset = (DefaultTableModel) PostfixTable.getModel();
        reset.setRowCount(0);

    }//GEN-LAST:event_Reset1ActionPerformed

    private void PrefixTitleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrefixTitleMousePressed
        WelcomeDash.setVisible(false);
        PostfixDash.setVisible(false);
        PrefixDash.setVisible(true);
        AboutDash.setVisible(false);

        PrefixNav.setBackground(new Color(255, 255, 255));
        PrefixTitle.setForeground(new Color(0, 0, 0));

        PostfixNav.setBackground(new Color(51, 51, 51));
        WelcomeNav.setBackground(new Color(51, 51, 51));
        AboutNav.setBackground(new Color(51, 51, 51));

        PrefixIndicator.setBackground(new Color(255, 208, 0));
        PostfixIndicator.setBackground(new Color(0, 0, 51));
        WelcomeIndicator.setBackground(new Color(0, 0, 51));
        AboutIndicator.setBackground(new Color(0, 0, 51));
    }//GEN-LAST:event_PrefixTitleMousePressed

    private void PrefixTitleMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrefixTitleMouseReleased
        WelcomeDash.setVisible(false);
        PostfixDash.setVisible(false);
        PrefixDash.setVisible(true);
        AboutDash.setVisible(false);

        PrefixNav.setBackground(new Color(51, 51, 51));
        PrefixTitle.setForeground(new Color(255, 255, 255));

        PostfixNav.setBackground(new Color(51, 51, 51));
        WelcomeNav.setBackground(new Color(51, 51, 51));
        AboutNav.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_PrefixTitleMouseReleased

    private void PrefixTitleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrefixTitleMouseEntered
        setColor(PrefixNav);
    }//GEN-LAST:event_PrefixTitleMouseEntered

    private void PrefixTitleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrefixTitleMouseExited
        resetColor(PrefixNav);
    }//GEN-LAST:event_PrefixTitleMouseExited

    private void Reset2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Reset2ActionPerformed

        // RESET BUTTON FUNCTION PREFIX GUI
        InfixInput2.setText("");
        DisplayInput2.setText("");
        PrefixOutput.setText("");
        DefaultTableModel reset = (DefaultTableModel) PrefixTable.getModel();
        reset.setRowCount(0);

    }//GEN-LAST:event_Reset2ActionPerformed

    private void BTSTitle1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_BTSTitle1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_BTSTitle1PropertyChange

    public static void main(String args[]) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataStructureGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>
        /*/
            Calls the Splash Screen Class That Displays the Splash Screen before opening the Main Class Window.
        /*/
        // LOADING SCREEN................
        SplashScreen wel = new SplashScreen();
        wel.setTitle("Brion Tactical Systems : Loading");
        wel.setVisible(true);

        DataStructureGUI enter = new DataStructureGUI();
        
        // Loading Number from 1 to 100 and Progress Bar
        try {
            for (int i = 0; i < 101; i++) {
                Thread.sleep(30);
                switch (i) {
                    case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 ->
                        wel.Status.setText("Loading   | " + Integer.toString(i) + "%");
                    case 20, 21, 22, 23, 24, 25, 26, 27, 28, 29 ->
                        wel.Status.setText("Loading   | " + Integer.toString(i) + "%");
                    case 30, 31, 32, 33, 34, 35, 36, 37, 38, 39 ->
                        wel.Status.setText("Loading   | " + Integer.toString(i) + "%");
                    case 40, 41, 42, 43, 44, 45, 46, 47, 48, 49 ->
                        wel.Status.setText("Loading   | " + Integer.toString(i) + "%");
                    case 50, 51, 52, 53, 54, 55, 56, 57, 58, 59 ->
                        wel.Status.setText("Loading   | " + Integer.toString(i) + "%");
                    case 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79 ->
                        wel.Status.setText("Starting  | " + Integer.toString(i) + "%");
                    case 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101 ->
                        wel.Status.setText("Starting  | " + Integer.toString(i) + "%");
                    default -> {
                    }
                }
                wel.Loading.setValue(i);        // Loading is a name of progressbar
            }
        } catch (InterruptedException e) {
        }

        // DELAY BEFORE DISPLAYING THE MAIN WINDOW POP UP WINDOW
        try {
            Thread.sleep(500);
            wel.setVisible(false);
            enter.setTitle("Brion Tactical Systems : Stack Implementation");
            enter.setVisible(true);
            wel.dispose();

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AboutDash;
    private javax.swing.JPanel AboutIndicator;
    private javax.swing.JLabel AboutLogo;
    private javax.swing.JLabel AboutLogoShad;
    private javax.swing.JPanel AboutNav;
    private javax.swing.JLabel AboutTitle;
    private javax.swing.JLabel BTSShadow;
    private javax.swing.JLabel BTSShadow1;
    private javax.swing.JLabel BTSTitle;
    private javax.swing.JLabel BTSTitle1;
    private javax.swing.JLabel CompanyLogo;
    private javax.swing.JLabel DisplayInput1;
    public javax.swing.JLabel DisplayInput2;
    private javax.swing.JButton EnterButton1;
    public javax.swing.JButton EnterButton2;
    private javax.swing.JButton Exit;
    private javax.swing.JTextField InfixInput1;
    public javax.swing.JTextField InfixInput2;
    private javax.swing.JLabel InfixLabel1;
    public javax.swing.JLabel InfixLabel2;
    private javax.swing.JLabel Line1;
    private javax.swing.JLabel Line2;
    private javax.swing.JLabel Line3;
    private javax.swing.JLabel Line4;
    private javax.swing.JLabel Line5;
    private javax.swing.JLabel Line6;
    private javax.swing.JLabel Line7;
    private javax.swing.JLabel Line8;
    private javax.swing.JLabel Line9;
    private javax.swing.JLabel LogoShadow;
    private javax.swing.JButton Minimize;
    private javax.swing.JLabel MyPicture;
    private javax.swing.JPanel NavigationBar;
    private javax.swing.JLabel NavigationLabel;
    private javax.swing.JPanel NavigationTitle;
    private javax.swing.JLabel PostFixDashTitle;
    private javax.swing.JPanel PostfixDash;
    private javax.swing.JPanel PostfixIndicator;
    private javax.swing.JLabel PostfixLabel;
    public javax.swing.JLabel PostfixLabel1;
    private javax.swing.JPanel PostfixNav;
    private javax.swing.JLabel PostfixResult;
    private javax.swing.JScrollPane PostfixScrollPanel;
    private javax.swing.JTable PostfixTable;
    private javax.swing.JLabel PostfixTitle;
    public javax.swing.JLabel PreFixDashTitle;
    private javax.swing.JPanel PrefixDash;
    private javax.swing.JPanel PrefixIndicator;
    private javax.swing.JPanel PrefixNav;
    public javax.swing.JLabel PrefixOutput;
    public javax.swing.JScrollPane PrefixScrollPanel;
    public javax.swing.JTable PrefixTable;
    private javax.swing.JLabel PrefixTitle;
    private javax.swing.JButton Reset1;
    public javax.swing.JButton Reset2;
    private javax.swing.JLabel StackShadow;
    private javax.swing.JLabel StackShadow1;
    private javax.swing.JLabel StackTitle;
    private javax.swing.JLabel StackTitle1;
    private javax.swing.JPanel TitleBar;
    private javax.swing.JPanel WelcomeDash;
    private javax.swing.JLabel WelcomeDashTitle;
    private javax.swing.JPanel WelcomeIndicator;
    private javax.swing.JLabel WelcomeLine;
    private javax.swing.JLabel WelcomeLine1;
    private javax.swing.JLabel WelcomeLine2;
    private javax.swing.JLabel WelcomeLine3;
    private javax.swing.JLabel WelcomeLine4;
    private javax.swing.JLabel WelcomeLine5;
    private javax.swing.JLabel WelcomeLine6;
    private javax.swing.JLabel WelcomeLine7;
    private javax.swing.JLabel WelcomeLine8;
    private javax.swing.JPanel WelcomeNav;
    private javax.swing.JLabel WelcomeTitle;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
