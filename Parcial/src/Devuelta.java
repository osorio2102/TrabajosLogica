import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Devuelta extends JFrame {
    private final int[] denominaciones = {100000, 50000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50};
    private final String[] tipo = {"Billete", "Billete", "Billete", "Billete", "Billete", "Billete", "Billete", "Moneda", "Moneda", "Moneda", "Moneda"};
    private JTextField txtDevuelta;
    private JTable tblExistencias;
    private DefaultTableModel Existencias;
    private JTable tblResultados;
    private DefaultTableModel Tabla;
    
    public Devuelta() {
        setTitle("Caja Registradora");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.add(new JLabel("Monto de la devuelta:"));
        txtDevuelta = new JTextField(10);
        panelSuperior.add(txtDevuelta);
        JButton btnCalcular = new JButton("Calcular Devuelta");
        panelSuperior.add(btnCalcular);
        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelTablas = new JPanel(new GridLayout(2, 1));
        
        Existencias = new DefaultTableModel(new String[]{"Denominación", "Tipo", "Cantidad"}, 0);
        tblExistencias = new JTable(Existencias);
        JScrollPane spExistencias = new JScrollPane(tblExistencias);
        panelTablas.add(spExistencias);
        
        for (int i = 0; i < denominaciones.length; i++) {
            Existencias.addRow(new Object[]{"$" + denominaciones[i], tipo[i], 0});
        }

        Tabla = new DefaultTableModel(new String[]{"Denominación", "Tipo", "Cantidad"}, 0);
        tblResultados = new JTable(Tabla);
        JScrollPane spTabla = new JScrollPane(tblResultados);
        panelTablas.add(spTabla);
        
        add(panelTablas, BorderLayout.CENTER);
        
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularDevuelta();
            }
        });
    }
    
    private void calcularDevuelta() {
        int valorDevuelta = Integer.parseInt(txtDevuelta.getText());
        int[] existencia = new int[denominaciones.length];
        int[] cantidadUsada = new int[denominaciones.length];
        
        for (int i = 0; i < denominaciones.length; i++) {
            existencia[i] = Integer.parseInt(Existencias.getValueAt(i, 2).toString());
        }
        
        for (int i = 0; i < denominaciones.length; i++) {
            if (valorDevuelta >= denominaciones[i]) {
                int cantidadNecesaria = valorDevuelta / denominaciones[i];
                cantidadUsada[i] = Math.min(cantidadNecesaria, existencia[i]);
                valorDevuelta -= cantidadUsada[i] * denominaciones[i];
            }
        }
        
        Tabla.setRowCount(0);
        if (valorDevuelta > 0) {
            JOptionPane.showMessageDialog(this, "No se puede dar la devuelta exacta.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            for (int i = 0; i < denominaciones.length; i++) {
                if (cantidadUsada[i] > 0) {
                    Tabla.addRow(new Object[]{"$" + denominaciones[i], tipo[i], cantidadUsada[i]});
                }
            }
        }
    }
  
}
