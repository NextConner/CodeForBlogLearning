package main

import "fmt"

type example struct{
	flag bool
	counter int16
	pi float32
}

func mian()  {
	
	

	/*
		1.声明和初始化
			声明一个 strcut 类型需要分配多少内存空间？
				1.bool 类型 1字节，int16 2字节(16bit)，float32 4字节 ， 一共是 7字节，但实际上是 8 字节
				2.这里引申出一个新概念 : 对齐填充
				3.对齐的概念： 对于磁盘来说，在它的对齐边界读取内存数据是十分高效的;由于数据的大小是一个特定的值，go语言会决定需要
				进行对齐填充的地方
				4.每两个字节的值必须跟随着两个字节的值，在struct 中由于 bool 1字节，int16 2字节，只有3字节，而且之后的 float32 是4字节长度，
				所以对齐的位置在 bool 和 int16 之间
				*/
				fmt.Printf("go where");
	fmt.Printf("test")

}