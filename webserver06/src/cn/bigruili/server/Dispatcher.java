package cn.bigruili.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class Dispatcher implements Runnable{
	private Socket client;
	private Response rs;
    private Request rq;
   public Dispatcher(Socket client){
	   this.client=client;
	   try {
		     //实例化封装请求对象
		       rq=new Request(client);
		          //实例化封装响应对象
		            rs=new Response(client);
		  } catch (Exception e) {
			  this.release();
	  }
   }
	@Override
	public void run() {
		try {
			if(rq.getUrl()==null||rq.getUrl().equals("")){
				InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("cn/bigruili/server/index.html");	
				rs.print(readAllBytes(is));
				rs.pushToBrowser(200);	
				is.close();
				return;
			}
			//实例化Servlet
			Servlet se=WebApp.getServletFromUrl(rq.getUrl());
			System.out.println(se);
			if(se!=null){
				se.service(rq, rs);
				//推
				rs.pushToBrowser(200);			
			}else{
				//错误页面
				InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("cn/bigruili/server/error.html");
				rs.print(this.readAllBytes(is));
				rs.pushToBrowser(404);	
				is.close();
			}
		} catch (Exception e) {
			try {
				rs.print("你好：我不好，我会马上好");
				rs.pushToBrowser(500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}		
		}
		release();
	}
public void release(){
	try {
		if(client!=null){
		client.close();
		}
		if(rs!=null){
			rs.release();
		}
		if(rq!=null){
			rq.release();
		}
		
	} catch (IOException e) {
		e.printStackTrace();
	}
}
public String readAllBytes(InputStream is){
	byte[] flush = new byte[1024*1024]; 
	int len = -1; 
	try {
		while((len=is.read(flush))!=-1) {
			String str = new String(flush,0,len);
			System.out.println(str);
			return str;
		}
	} catch (IOException e) {
		e.printStackTrace();
		System.out.println("读html出错");
	}
	return null;
}
}
