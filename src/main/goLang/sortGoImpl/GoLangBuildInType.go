package main

import "fmt"

func main() {
	fmt.Println("hello","world")	
	
	/*
	内嵌数据类型：
	1.数据类型具备完整性和可读性
		1.当我们分配内存时，分配的内存大小是多少？
		2.这个内存大小代表着什么？
	2.数据类型可以时具体的，例如 int32 ,int64
		1. 包含10位数字的 uint8（无符号8位整型） 类型占用 一个字节
		2. 包含10位数字的 int32(有符号32位整型) 类型占用4个字节
	3.当声明了一个类型没有指定值时，会分配一个默认值
	4.字长代表一个字所占用的字节数，和内存地址长度相匹配，例如在64位架构系统中，一个字长就是 64位(8 字节)，地址长度也是64，因此
	int 类型长度时 64位
	
	5. 零值概念:
		1. 所有我们创建的 字面量 都必须进行初始化再使用，如果没有指定初始值，将会默认对它们分配'零值',分配的内存大小也是 0 bit
			类型默认值：
				 Boolean : false
				 Integer : 0
				 Floating Point : 0
				 Complex : 0i
				 String  ：""
				 Pointer : nil
		2.字符类类型由一系列的 uint8 类型组成，字符类型结构分为两段：
			1.第一段指向存储内容的数组地址，
			2.第二段代表数组的长度
			3.空字符串第一段为nil,第二段为 0
	*/

	//输出下列类型的零值
	var a int
	var b string
	var c float64
	var d bool
	fmt.Printf("var a int \t %T [%v]\n" ,a,a)
	fmt.Printf("var a string \t %T [%v]\n" ,b,b)
	fmt.Printf("var a float64 \t %T [%v]\n" ,c,c)
	fmt.Printf("var a bool \t %T [%v]\n" ,d,d)

	/*
		转换和转型:
			go 中没有转型的概念，通过分配更多的内存的方式，代替告知编译器去假装获取更多字节
	*/
	//指定类型然后转换
	aaa := int32(10)
	fmt.Printf(" aaa := int32(10) %T [%v] \n",aaa,aaa)


}


