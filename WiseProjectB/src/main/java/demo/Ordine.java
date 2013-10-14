package demo;



public class Ordine {

    protected long numeroOrdine;
    protected Cliente customer;
    protected ElementoOrdine[] items;

    public long getNumeroOrdine() {
        return numeroOrdine;
    }

    public void setNumeroOrdine(long value) {
        this.numeroOrdine = value;
    }

    public Cliente getCliente() {
        return customer;
    }

    public void setCliente(Cliente value) {
        this.customer = value;
    }

    public ElementoOrdine[] getElementiOrdine() {
        return this.items;
    }
    
    public void setElementiOrdine(ElementoOrdine[] elems) {
    	this.items = elems;
    }

}
