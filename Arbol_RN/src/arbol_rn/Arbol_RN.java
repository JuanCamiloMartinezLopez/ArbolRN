package arbol_rn;

/**
 *
 * @author Karen
 */
public class Arbol_RN<T extends Comparable<T>>
{
	public Nodo<T> raizArbol;
	
	public Arbol_RN()
	{
		raizArbol = null;
	}
	
	public void print()
	{
		System.out.println("");
		inOrder(raizArbol);

	}
	
	public void preOrder(Nodo<T> nodo)
	{
		if(nodo != null)
		{
			System.out.print(nodo.toString());
			preOrder(nodo.Hijoizq);
			preOrder(nodo.Hijoder);
		}
	}

	public String inOrder(Nodo<T> nodo)
	{
		String nodoInfo="";
		if(nodo.valorNodo != null && nodo.nombreNodo!=null)
		{
			
			nodoInfo=inOrder(nodo.Hijoizq)+String.valueOf(nodo.valorNodo)+"( "+nodo.nombreNodo+" ),"+inOrder(nodo.Hijoder);
			
		}else {
			nodoInfo="";
		}
		return nodoInfo;
	}
	
	public Nodo<T> busqueda(T value)
	{
		Nodo<T> p = raizArbol;
		Nodo<T> pre = null;
		while(true)
		{
			if(value.compareTo(p.valorNodo) == 0)
			{
				return p;
			}
			else if(value.compareTo(p.valorNodo) == -1)
			{
				pre = p;
				p = p.Hijoizq;
				if(p.hoja == true)
					return pre;
			}
			else if(value.compareTo(p.valorNodo) == 1)
			{
				pre = p;
				p = p.Hijoder;
				if(p.hoja == true)
					return pre;
			}
		}
	}
	
	private void RotacionIzq(Nodo<T> nodo)
	{
		Nodo<T> p = nodo.Hijoder;
		if(nodo.Padre != null)
		{
			if(nodo.Padre.Hijoizq == nodo)
			{
				nodo.Padre.Hijoizq = p;
				p.Padre = nodo.Padre;
			}
			else
			{
				nodo.Padre.Hijoder = p;
				p.Padre = nodo.Padre;
			}
		}
		else
		{
			raizArbol = p;
			p.Padre = null;
		}
		nodo.Hijoder = nodo.Hijoder.Hijoizq;
		p.Hijoizq = nodo;
		
		nodo.Hijoder.Padre = nodo;
		nodo.Padre = p;
	}

	private void Rotacionder(Nodo<T> nodo)
	{
		Nodo<T> p = nodo.Hijoizq;
		if(nodo.Padre != null)
		{
			if(nodo.Padre.Hijoizq == nodo)
			{
				nodo.Padre.Hijoizq = p;
				p.Padre = nodo.Padre;
			}
			else
			{
				nodo.Padre.Hijoder = p;
				p.Padre = nodo.Padre;
			}
		}
		else
		{
			raizArbol = p;
			p.Padre = null;
		}
		nodo.Hijoizq = nodo.Hijoizq.Hijoder;
		p.Hijoder = nodo;
		
		nodo.Hijoizq.Padre = nodo;
		nodo.Padre = p;
	}
	
	public void agregar(T value, String nombre)
	{
		if(raizArbol == null)
		{
			raizArbol = new Nodo<T>(value, nombre, "BLACK");
			raizArbol.Hijoizq = new Nodo<T>(raizArbol);
			raizArbol.Hijoder = new Nodo<T>(raizArbol);
		}
		else									
		{
			Nodo<T> nodo = null;
			nodo = busqueda(value);
			if(value.compareTo(nodo.valorNodo) == 0)
			{
				System.out.println("");
				return;
			}
			else
			{
				Nodo<T> newNode = new Nodo<T>(value, nombre, "RED");
				newNode.Hijoizq = new Nodo<T>(newNode);
				newNode.Hijoder = new Nodo<T>(newNode);
				newNode.Padre = nodo;
				if(value.compareTo(nodo.valorNodo) == -1)
				{
					nodo.Hijoizq = newNode;
				}
				else
				{
					nodo.Hijoder = newNode;
				}
					
				ajustarIngreso(newNode);
			}
		}
	}
	
	private void ajustarIngreso(Nodo<T> newNode)
	{
		Nodo<T> parentNode = newNode.Padre;
		if(parentNode.getColor().equals("BLACK"))
		{
			return;
		}
		else
		{
			Nodo<T> grandNode = newNode.Padre.Padre;
				if(grandNode.Hijoizq.getColor().equals("RED") && 	grandNode.Hijoder.getColor().equals("RED"))
				{
					grandNode.Hijoizq.setColor("BLACK");
					grandNode.Hijoder.setColor("BLACK");
					if(grandNode.Padre == null)
						return;
					else
					{
						grandNode.setColor("RED");
						ajustarIngreso(grandNode);
					}
				}
				else
				{
					if(grandNode.Hijoizq.Hijoizq == newNode)
					{
						grandNode.setColor("RED");
						parentNode.setColor("BLACK");
						Rotacionder(grandNode);
						
					}
					else if(grandNode.Hijoizq.Hijoder == newNode)
					{
						RotacionIzq(parentNode);
						grandNode.setColor("RED");
						newNode.setColor("BLACK");
						Rotacionder(grandNode);
					}
					else if(grandNode.Hijoder.Hijoder == newNode)
					{
						grandNode.setColor("RED");
						parentNode.setColor("BLACK");
						RotacionIzq(grandNode);
					}
					else if(grandNode.Hijoder.Hijoizq == newNode)
					{
						Rotacionder(parentNode);
						grandNode.setColor("RED");
						newNode.setColor("BLACK");
						RotacionIzq(grandNode);
					}
				}
		}
	}
	
	public Boolean eliminar(T value)
	{
		Nodo<T> dNode = null;
		dNode = busqueda(value);
		if(dNode.valorNodo.compareTo(value) != 0)
		{
			System.out.println("Codigo no encontrado");
			return false;
		}
		else
		{
			if(dNode.Hijoizq.hoja == false && dNode.Hijoder.hoja == false)
			{
				T sValue = sucesor(dNode);
				String sNombre= sucesorNombre(dNode);
				eliminar(sucesor(dNode));
				dNode.valorNodo = sValue;
				dNode.nombreNodo=sNombre;
			}
			else
			{
				if(dNode.getColor().equals("RED"))
				{
					if(dNode.Padre.Hijoizq == dNode)
					{
						if(dNode.Hijoizq.hoja == false)
						{
							dNode.Padre.Hijoizq = dNode.Hijoizq;
							dNode.Hijoizq.Padre = dNode.Padre;
						}
						else if(dNode.Hijoder.hoja == false)
						{
							dNode.Padre.Hijoizq = dNode.Hijoder;
							dNode.Hijoder.Padre = dNode.Padre;
						}
						else
						{
							dNode.Padre.Hijoizq = dNode.Hijoizq;
						}
						dNode.Hijoizq = dNode.Hijoder = dNode.Padre = null;
					}
					else
					{
						if(dNode.Hijoizq.hoja == false)
						{
							dNode.Padre.Hijoder = dNode.Hijoizq;
							dNode.Hijoizq.Padre = dNode.Padre;
							
						}
						else if(dNode.Hijoder.hoja == false)
						{
							dNode.Padre.Hijoder = dNode.Hijoder;
							dNode.Hijoder.Padre = dNode.Padre;
						}
						else
						{
							dNode.Padre.Hijoder = dNode.Hijoizq;
						}
						dNode.Hijoizq = dNode.Hijoder = dNode.Padre = null;
					}
				}
				else
				{
					if(raizArbol == dNode)
					{
						if(dNode.Hijoizq.hoja == false)
						{
							dNode.Hijoizq.setColor("BLACK");
							raizArbol = dNode.Hijoizq;
							dNode.Hijoizq.Padre = null;
							return true;
						}
						raizArbol = null;
						return true;
					}
					if(dNode.Hijoizq.hoja == false)
					{
						if(dNode.Padre.Hijoizq == dNode)
						{
							dNode.Padre.Hijoizq = dNode.Hijoizq;
							dNode.Hijoizq.Padre = dNode.Padre;
						}
						else
						{
							dNode.Padre.Hijoder = dNode.Hijoizq;
							dNode.Hijoizq.Padre = dNode.Padre;
						}
						dNode.Hijoizq.setColor("BLACK");
						dNode.Hijoizq = dNode.Hijoder = dNode.Padre = null;
					}
					else if(dNode.Hijoder.hoja == false)
					{
						if(dNode.Padre.Hijoizq == dNode)
						{
							dNode.Padre.Hijoizq = dNode.Hijoder;
							dNode.Hijoder.Padre = dNode.Padre;
						}
						else
						{
							dNode.Padre.Hijoder = dNode.Hijoder;
							dNode.Hijoder.Padre = dNode.Padre;
						}
						dNode.Hijoder.setColor("BLACK");
						dNode.Hijoizq = dNode.Hijoder = dNode.Padre = null;
					}
					else
					{
						if(dNode.Padre.Hijoizq == dNode)
						{
							dNode.Padre.Hijoizq = dNode.Hijoizq;
							eliminar_hN(dNode.Padre, "LEFT");
						}
						else
						{
							dNode.Padre.Hijoder = dNode.Hijoizq;
							eliminar_hN(dNode.Padre, "RIGHT");
						}
						
						dNode.Hijoizq = dNode.Hijoder = dNode.Padre = null;
					}
				}
			}
		}
		System.gc();
		return true;
	}

	private void eliminar_hN(Nodo<T> nodo, String side)
	{
		if(nodo.Hijoizq.getColor().equals("RED"))
		{
			nodo.setColor("RED");
			nodo.Hijoizq.setColor("BLACK");
			Rotacionder(nodo);
			eliminar_hN(nodo, side);
		}
		else if(nodo.Hijoder.getColor().equals("RED"))
		{
			nodo.setColor("RED");
			nodo.Hijoder.setColor("BLACK");
			RotacionIzq(nodo);
			eliminar_hN(nodo, side);
		}
		else
		{
			if(side.equals("LEFT"))
			{
				if(nodo.Hijoder.Hijoder.getColor().equals("RED"))
				{
					nodo.Hijoder.Hijoder.setColor("BLACK");
					if(nodo.getColor().equals("RED"))
					{
						nodo.setColor("BLACK");
						nodo.Hijoder.setColor("RED");
					}
					RotacionIzq(nodo);
					return;
				}
				else if(nodo.Hijoder.Hijoizq.getColor().equals("RED"))
				{
					nodo.Hijoder.setColor("RED");
					nodo.Hijoder.Hijoizq.setColor("BLACK");
					Rotacionder(nodo.Hijoder);
					
					nodo.Hijoder.Hijoder.setColor("BLACK");
					if(nodo.getColor().equals("RED"))
					{
						nodo.setColor("BLACK");
						nodo.Hijoder.setColor("RED");
					}
					RotacionIzq(nodo);
					return;
				}
				else
				{
					if(nodo.getColor().equals("RED"))
					{
						nodo.setColor("BLACK");
						nodo.Hijoder.setColor("RED");
						return;
					}
					else
					{
						nodo.Hijoder.setColor("RED");
						if(nodo.Padre == null)
							return;
						else
						{
							if(nodo.Padre.Hijoizq == nodo)
								eliminar_hN(nodo.Padre, "LEFT");
							else
								eliminar_hN(nodo.Padre, "RIGHT");
						}
					}
				}
			}
			else
			{
				if(nodo.Hijoizq.Hijoizq.getColor().equals("RED"))
				{
					nodo.Hijoizq.Hijoizq.setColor("BLACK");
					if(nodo.getColor().equals("RED"))
					{
						nodo.setColor("BLACK");
						nodo.Hijoizq.setColor("RED");
					}
					Rotacionder(nodo);
					return;
					
				}
				else if(nodo.Hijoizq.Hijoder.getColor().equals("RED"))
				{
					nodo.Hijoizq.setColor("RED");
					nodo.Hijoizq.Hijoder.setColor("BLACK");
					RotacionIzq(nodo.Hijoizq);
					
					nodo.Hijoizq.Hijoizq.setColor("BLACK");
					if(nodo.getColor().equals("RED"))
					{
						nodo.setColor("BLACK");
						nodo.Hijoizq.setColor("RED");
					}
					Rotacionder(nodo);
					return;
				}
				else
				{
					if(nodo.getColor().equals("RED"))
					{
						nodo.setColor("BLACK");
						nodo.Hijoizq.setColor("RED");
						return;
					}
					else
					{
						nodo.Hijoizq.setColor("RED");
						if(nodo.Padre == null)
							return;
						else
						{
							if(nodo.Padre.Hijoizq == nodo)
								eliminar_hN(nodo.Padre, "LEFT");
							else
								eliminar_hN(nodo.Padre, "RIGHT");
						}
					}
				}
			}
		}
		
	}

	private T sucesor(Nodo<T> dNode)
	{
		Nodo<T> p = null;
		p = dNode.Hijoder;
		while(p.Hijoizq.hoja == false)
		{
			p = p.Hijoizq;
		}
		return p.valorNodo;
	}
	private String sucesorNombre(Nodo<T> dNode)
	{
		Nodo<T> p = null;
		p = dNode.Hijoder;
		while(p.Hijoizq.hoja == false)
		{
			p = p.Hijoizq;
		}
		return p.nombreNodo;
	}
}

