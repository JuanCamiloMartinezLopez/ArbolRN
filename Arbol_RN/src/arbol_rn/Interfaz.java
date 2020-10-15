package arbol_rn;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Interfaz extends JFrame{
	
	public int flag;
	public int WinWidth = 1350;
	public int WinHeight = 700;
	public int LROffset = 170;
	public int DownOffset = 50;
	public int nodeD = 26;
	public int levelOffset = 50;
	public JPanel control;
	public JPanel lienzo;
	public JLabel Titulo;
	public JButton Ingresar;
	public JButton Eliminar;
	public JLabel labelIngresar;
	public JLabel labelCodigo;
	public JLabel labelCodigoE;
	public JLabel labelNombre;
	public JLabel labelEliminar;
	public JTextField IngresarCodigo;
	public JTextField IngresarNombre;
	public JTextField EliminarCodigo;
	public Graphics g;
	public JFrame f;
	public Arbol_RN<Integer> arbolRN;
	
	public Interfaz() {
		this.arbolRN=new Arbol_RN<>();;
		//f= this;
		f = new JFrame("Arbol Rojinegro");
		f.setLocationRelativeTo(null);
		f.setLayout(null);
		f.setLocation(0, 0);
		//f.setSize( WinWidth,WinHeight);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setUndecorated(true);
		f.setResizable(false);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Titulo= new JLabel("ARBOL ROJINEGRO");
		Titulo.setForeground(Color.RED);
		Titulo.setFont(new Font("Monospaced", Font.BOLD, 36));
		Titulo.setBounds((f.getWidth()/2)-175, 20, 350, 50);
		f.getContentPane().add(Titulo);
		
		control = new JPanel();
		control.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		control.setBounds((f.getWidth()/2)-320, 80, 640, 100);
		control.setLayout(null);
		f.getContentPane().add(control);
		
		
		lienzo= new JPanel();
		lienzo.setBounds(20, (int) (f.getHeight()*0.25), f.getWidth()-40, (int) (f.getHeight()-(f.getHeight()*0.3)));
		lienzo.setBackground(Color.WHITE);
		lienzo.setBorder(BorderFactory.createLineBorder(Color.black));
		lienzo.setAutoscrolls(true);
		f.getContentPane().add(lienzo);
		
		g = lienzo.getGraphics();
		
		labelIngresar = new JLabel("Ingresar Nodo");
		labelIngresar.setBounds(120, 10, 100, 20);
		control.add(labelIngresar);
		labelCodigo= new JLabel("Codigo");
		labelCodigo.setBounds(120, 40, 50, 20);
		control.add(labelCodigo);
		labelNombre= new JLabel("Nombre");
		labelNombre.setBounds(210, 40, 50, 20);
		control.add(labelNombre);
		IngresarCodigo= new JTextField();
		IngresarCodigo.setBounds(175, 40, 25, 20);
		IngresarCodigo.setVisible(true);
		control.add(IngresarCodigo);
		IngresarNombre= new JTextField();
		IngresarNombre.setBounds(265, 40, 50, 20);
		control.add(IngresarNombre);
		Ingresar=new JButton("Ingresar");
		Ingresar.setBounds(170, 70, 100, 20);
		Ingresar.addActionListener(new ActionListener(){  
			@Override
			public void actionPerformed(ActionEvent e) {
				g.drawOval(0, 0, 10, 10);
				int codigo;
				String nombre= IngresarNombre.getText();
				try {
					codigo=Integer.parseInt(IngresarCodigo.getText());
					System.out.println(String.valueOf(codigo));
					arbolRN.agregar(codigo,nombre);
					repintar();
				}catch(Exception error) {
					System.out.println(error.toString());	
					pintarMensaje("Codigo Invalido");
				}
				IngresarNombre.setText("");
				IngresarCodigo.setText("");
			}  
	    });
		control.add(Ingresar);
		
		labelEliminar= new JLabel("Eliminar Nodo");
		labelEliminar.setBounds(375, 10, 100, 20);
		control.add(labelEliminar);
		labelCodigoE= new JLabel("Codigo");
		labelCodigoE.setBounds(375, 40, 50, 20);
		control.add(labelCodigoE);
		EliminarCodigo=new JTextField();
		EliminarCodigo.setBounds(430, 40, 25, 20);
		control.add(EliminarCodigo);
		Eliminar=new JButton("Eliminar");
		Eliminar.setBounds(380, 70, 100, 20);
		Eliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int codigo;
				try {
					codigo=Integer.parseInt(EliminarCodigo.getText());
					System.out.println(String.valueOf(codigo));
					if(arbolRN.eliminar(codigo)) {
						repintar();
					}else {
						pintarMensaje("Codigo inexistente");
					}
					
				}catch(Exception error) {
					System.out.println(error);	
					pintarMensaje("Codigo Invalido");
				}
				EliminarCodigo.setText("");
			}
			
		});
		control.add(Eliminar);
		
		control.revalidate();
		control.repaint();
	}
	
	public void repintar()
	{
		lienzo.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		paintArbolRN(arbolRN.raizArbol, g2, (lienzo.getWidth()/2)-13, 5, 0);
		//pintarInOrden(arbolRN.inOrder(arbolRN.raizArbol));
		
	}
	
	public void pintarMensaje(String mensaje) {
		g.setColor(Color.BLACK);
		g.drawString(mensaje, 2, 10);
	}
	
	public void pintarInOrden(String inOrden) {
		g.setColor(Color.BLACK);
		g.drawString("INORDEN: "+inOrden.substring(0, inOrden.length()-1), 2, lienzo.getHeight()-10);
	}
	private void paintArbolRN(Nodo node, Graphics2D g, int x, int y, int level)
	{
		
		if(node != null)
		{
			int nodeR = nodeD/2;
			int nextLNodeX = x - LROffset +level*levelOffset;
			int nextRNodeX = x + LROffset - level*levelOffset;
			int nextNodeY = y + DownOffset;
			if(node.hoja ==false)
			{
				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(2));
				if(node.Hijoizq.hoja == false)
					g.drawLine(nextLNodeX + nodeR, nextNodeY + nodeR, x + nodeR , y + nodeR);
				if(node.Hijoder.hoja == false)
					g.drawLine(nextRNodeX + nodeR, nextNodeY + nodeR, x + nodeR, y + nodeR);
				if(node.getColor().equals("RED"))
					g.setColor(Color.RED);
				else
					g.setColor(Color.BLACK);
				g.fillOval(x, y, nodeD - level, nodeD - level);
				g.drawString(node.nombreNodo,x+nodeD+2 ,  y+ nodeR+4);
				g.setColor(Color.WHITE);
				g.drawString(String.valueOf(node.valorNodo), x + nodeR-10, y+ nodeR+4);
				
			}
			else
			{
				/*g.setColor(Color.BLACK);
				g.fillRect(x, y + nodeR, nodeD, nodeD/2);*/
				
			}
			paintArbolRN(node.Hijoizq, g, nextLNodeX,  nextNodeY, level + 1);
			paintArbolRN(node.Hijoder, g, nextRNodeX, nextNodeY, level + 1);
		}
	}
	
	public static void main(String []Args) {
		new Interfaz();
	}

}
