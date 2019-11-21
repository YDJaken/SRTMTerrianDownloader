package com.dy.ObjFileProperty;

import com.dy.Util.OBJUtil;

public class run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(args.length);
		if(args.length>0) {
			System.out.println(OBJUtil.computeArea(args[0])); 
		}else {			
			new MainFrame();
		}
	}

}
