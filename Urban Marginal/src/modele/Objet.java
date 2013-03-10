package modele;

public abstract class Objet {
	
	protected int posX;
	protected int posY;
	protected Label label;
	
	//--- getter posX ---
	public int getPosX(){
		return posX;
	}
	
	//--- getter posY ---
	public int getPosY(){
		return posY;
	}
	
	//--- setter posX ---
	public void setPosX(int posX){
		this.posX = posX;
	}
	
	//--- setter posY ---
	public void setPosY(int posY){
		this.posY = posY;
	}
	
	//--- getter label ---
	public Label getLabel(){
		return label;
	}
	
	//--- Un objet touche un autre objet ---
	public boolean toucheObjet(Objet objet){
		if(objet.label==null){
			return false;
		}else{
			if(objet.label.getJLabel()==null){
				return false;
			}else{
				int l_obj = objet.label.getJLabel().getWidth();
				int h_obj = objet.label.getJLabel().getHeight();
				int l_this = this.label.getJLabel().getWidth();
				int h_this = this.label.getJLabel().getHeight();
				return(!((this.posX+l_this<objet.posX || 
						this.posX>objet.posX+l_obj) ||
						(this.posY+h_this<objet.posY ||
								this.posY>objet.posY+h_obj)));
			}
		}
	}
}
