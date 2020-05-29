package main

import "fmt"
import "math"

//定义树结构
type treeNode struct{
	value int32 
	left *treeNode 
	right *treeNode 
}

func isBalancedTree(root *treeNode) bool{
	var flag bool=false
	if(nil == root){
		flag = true;
	}else{
		left := root.left
		right := root.right
		flag = (math.Abs(treeHeight(left) - treeHeight(right)) <= 1 &&  isBalancedTree(left) && isBalancedTree(right))
	}
	return flag
}

func treeHeight(root *treeNode)float64{

	var height float64
	var maxHeight float64 = 0
	if(nil == root){
		height = 0
	}else{
		var left  *treeNode = root.left
		var right *treeNode = root.right
		if(treeHeight(left) > treeHeight(right)){
			maxHeight =  treeHeight(left)
		}else{
			maxHeight =  treeHeight(right)
		}
	}

	return height + maxHeight
}

func main()  {
	
}