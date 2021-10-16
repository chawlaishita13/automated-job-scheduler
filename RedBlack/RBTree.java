package RedBlack;


public class RBTree<T extends Comparable, E> implements RBTreeInterface<T, E>  {

	private RedBlackNode root;
	boolean flag=false;
	
	public void inorder(RedBlackNode<T,E> node) {
		if(node==null)
			return;
		inorder(node.left);
		System.out.print(node.value+" "+node.colour+" ");
		inorder(node.right);
	}
	
    @Override
    public void insert(T key, E value) {
    	flag=false;
    	RedBlackNode nn=new RedBlackNode(key,value);
    	if(root==null) {
			nn.colour=1;
			root=nn;
			
		}
    
		else {
			insert(root,nn);
			if(flag==false&& nn.parent.colour==0)
				fun(nn);
				
			}
    	
    	nn.list.add(nn.value);
    	

    }
    private void insert(RedBlackNode node, RedBlackNode nn) {
    	if (node == null) {
			return;
		}
		
		else if (nn.key.toString().compareTo(node.key.toString()) > 0) {
			if (node.right == null) {
				node.right = nn;
				nn.parent=node;
				
			} else
				insert(node.right,nn);
			
		}
		
		else if(nn.key.toString().compareTo(node.key.toString()) < 0) {
			if (node.left == null) {
				node.left = nn;
				nn.parent=node;
				
			} else
				insert(node.left,nn);
		}
		
		else {
			node.list.add(nn.value);
			flag=true;	
			}
    }
    
    
    
    void leftRot(RedBlackNode node) 
    { 
    	RedBlackNode rc=node.right;
    	RedBlackNode par=node.parent;
    	if(par!=null && par.right==node) {
    		par.right=rc;
    		rc.parent=par;
    		node.right=rc.left;
    		rc.left=node;
    		
    		node.parent=rc;
    		if(node.right!=null)
    			node.right.parent=node;
    	}
    	else if(par!=null && par.left==node) {
    		par.left=rc;
    		rc.parent=par;
    		node.right=rc.left;
    		rc.left=node;
    		
    		node.parent=rc;
    		if(node.right!=null)
    			node.right.parent=node;
    		}
    	
    	else if(par==null) {
    		node.right=rc.left;
    		node.parent=rc;
    		rc.left=node;
    		root=rc;
    		rc.parent=null;
    		if(node.right!=null)
    			node.right.parent=node;
    	}
    	

    } 
    
    void rightRot(RedBlackNode node) 
    { 
    	RedBlackNode lc=node.left;
    	RedBlackNode par=node.parent;
    	if(par!=null && par.right==node) {
    		par.right=lc;
    		lc.parent=par;
    		node.left=lc.right;
    		lc.right=node;
    		node.parent=lc;
    		if(node.left!=null)
    			node.left.parent=node;
    	}
    	else if(par!=null && par.left==node) {
    		par.left=lc;
    		lc.parent=par;
    		node.left=lc.right;
    		lc.right=node;
    		node.parent=lc;
    		if(node.left!=null)
    			node.left.parent=node;
    		}
    	
    	else if(par==null) {
    		node.left=lc.right;
    		node.parent=lc;
    		lc.right=node;
    		root=lc;
    		lc.parent=null;
    		if(node.left!=null)
    			node.left.parent=node;
    	}
    	

    } 
    
    public void fun(RedBlackNode node) {
    		if(node==root)
    			node.colour=1;
    		else if(node.parent==root) {
    			
    		}
    		else {
			RedBlackNode grandp=node.parent.parent;
			if(grandp.left==node.parent) {
				RedBlackNode uncle=grandp.right;
				if(uncle==null) 
				{
					if(node==node.parent.left) {
						rightRot(grandp);
						grandp.colour=0;
						grandp.parent.colour=1;
					}
					else {
						leftRot(node.parent);
						rightRot(grandp);
						grandp.colour=0;
						grandp.parent.colour=1;
					}
					
				}
				else {
					if(uncle.colour==0) {
						grandp.colour = 0; 
		                node.parent.colour = 1; 
		                uncle.colour=1; 
		                fun(grandp); 
					}
					else {
						if(node==node.parent.left) {
							rightRot(grandp);
							grandp.colour=0;
							grandp.parent.colour=1;
						}
						else {
							leftRot(node.parent);
							rightRot(grandp);
							grandp.colour=0;
							grandp.parent.colour=1;
						}
					}
				}
					
			}	
			else{
				RedBlackNode uncle=grandp.left;
				if(uncle==null) 
				{
					if(node==node.parent.right) {
						leftRot(grandp);
						grandp.colour=0;
						grandp.parent.colour=1;
					}
					else {
						rightRot(node.parent);
						leftRot(grandp);
						grandp.colour=0;
						grandp.parent.colour=1;
					}
				}
				else {
					if(uncle.colour==0) {
						grandp.colour = 0; 
		                node.parent.colour = 1; 
		                uncle.colour=1; 
		                fun(grandp); 
					}
					else {
						if(node==node.parent.right) {
							leftRot(grandp);
							grandp.colour=0;
							grandp.parent.colour=1;
						}
						else {
							rightRot(node.parent);
							leftRot(grandp);
							grandp.colour=0;
							grandp.parent.colour=1;
						}
					}
				}
					
			}	
			
			
		
    }}
    public RedBlackNode<T, E> search(T key) {
    	if(contains(key))
    		return search(key,root);
    	else {
    		RedBlackNode<T,E> nn=new RedBlackNode<T,E>(key,null);
    		nn.list=null;
    		return nn;
    		
    	}
	}
      
    private RedBlackNode search(T key,RedBlackNode node) {
		
		
		if((key.toString().equals(node.key.toString()))){
			return node;
		}
		else if(key.toString().compareTo(node.key.toString())>0) {
			return search(key,node.right);
		}
		else
			return search(key,node.left);
	}
		
	
		
	
	
	public boolean contains(T key) {
		return contains(key,root);
	}
	
	private boolean contains(T key,RedBlackNode node) {
		if(node==null) {
			return false;
		}
		
		if(key.toString().equals(node.key.toString()))
			return true;
		
		else if(key.toString().compareTo(node.key.toString())>0) {
			return contains(key,node.right);}
		
		else {
			return contains(key,node.left);}
		
		
	}

    
}