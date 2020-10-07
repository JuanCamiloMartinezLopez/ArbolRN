/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbol_rn;

/**
 *
 * @author Karen
 */

public class Nodo<T extends Comparable<T>>
{

    /*
       Se crea clase nodo y atributos
    */

    
	public T valorNodo;
    public String nombreNodo;
	public Nodo<T> Hijoizq;
	public Nodo<T> Hijoder;
	public Nodo<T> Padre;
	private String color;
	public boolean hoja;
	public Nodo(Nodo<T> Padre)	
	{
		valorNodo = null;
		Hijoizq = Hijoder = null;
		this.Padre = Padre;
		color = "BLACK";
		hoja = true;
	}
	public Nodo(T valorNodo, String nombreNodo, String color)			//Ã¯Â¿Â½Ã¯Â¿Â½Ã¯Â¿Â½Ã¯Â¿Â½Ã¯Â¿Â½Ã¯Â¿Â½Ã�Â¨Ã¯Â¿Â½ÃšÂµÃ¯Â¿Â½
	{
		super();
                this.nombreNodo=nombreNodo;
		this.valorNodo = valorNodo;
		this.color = color;
		Hijoizq = Hijoder = Padre = null;
		hoja = false;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public void setColor(String color)
	{
		if(color == "BLACK" || color == "RED")
			this.color = color;
		else
			System.out.println("error");
			return;
	}
	
	public String toString()
	{
		return "[" + valorNodo+ ", " + nombreNodo+ "]  ";
	}
}

