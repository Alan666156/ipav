/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.54
 * Generated at: 2015-10-14 02:45:34 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.pages.system;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class main_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/tags/ipav.tld", Long.valueOf(1444725534827L));
    _jspx_dependants.put("/pages/system/common/head.jsp", Long.valueOf(1427537646000L));
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/pages/css/xt_style.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/pages/css/pt_style.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/pages/css/demo.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/pages/css/zTreeStyle.css\" />\r\n");
      out.write("<script type=\"text/javascript\" src=\"/pages/js/jquery-1.7.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"/pages/js/publicjs/msgBox.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"/pages/js/public.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"/pages/js/jquery.validate.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"/pages/js/jquery.metadata.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"/pages/js/common.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"/pages/js/jquery.ztree.core-3.5.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"/pages/js/jquery.ztree.excheck-3.5.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"/pages/js/tipswindown.js\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("</html>");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<title>欢迎登录快捷管家</title>\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("html,body,div,ul,li,ol,p,form,input,span,a,img,h1,h2,h3,h4,del,dl,dd,dt,textarea,label,fieldset{margin:0px;padding:0px;}\r\n");
      out.write("body{font-family:\"微软雅黑\",\"宋体\";  font-size:13px; height:100%;  color:#3e4d4d; }\r\n");
      out.write(".simScrollCont{display:none;position:absolute;width:100%;height:100%;background:#000;top:0;left:0;filter:alpha(Opacity=80);-moz-opacity:0;opacity:0.4;}\r\n");
      out.write(".simScrollContBox{position:fixed;_position:absolute;border:5px solid #E9F3FD;background:#FFF;text-align:left; width:542px; height:290px; display:none;}\r\n");
      out.write(".simScrollContBox2{ width:540px; height:288px; background:#fff; border:1px solid #48a8cf;}\r\n");
      out.write(".tctitle{ line-height:40px; color:#fff; text-indent:15px; width:540px; height:40px; background:#48a8cf; font-size:15px;}\r\n");
      out.write(".tctitle a{ float:right; width:19px; height:19px; background:url(../images/systems/close1.jpg); display:block; margin-top:10px; margin-right:10px;}\r\n");
      out.write(".tctitle a:hover{ background:url(../images/systems/close2.jpg);} \r\n");
      out.write(".four{ margin-top:-4px;}\r\n");
      out.write(".foure{ margin-bottom:-4px;}\r\n");
      out.write("</style>\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("\tfunction onloalrame(){\r\n");
      out.write("\t\tvar winhights = document.documentElement.clientHeight;\r\n");
      out.write("\t\tvar leftboxs = winhights-89;\r\n");
      out.write("\t\tvar mainboxs = winhights-89;\r\n");
      out.write("\t    document.getElementById(\"leftFrame\").style.height=leftboxs+\"px\";\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tdocument.getElementById(\"mainFrame\").style.height=mainboxs+\"px\";\r\n");
      out.write("\t\t}\r\n");
      out.write("\t$(document).ready(function(){\r\n");
      out.write("\t\t$(\"#mainframe\").load(function () { \r\n");
      out.write("\t        var mainheight = $(this).contents().find(\"body\").height(); \r\n");
      out.write("\t        $(this).height(mainheight); \r\n");
      out.write("\t    }); \r\n");
      out.write("\t})\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<!-- <frameset rows=\"119,*,55\" cols=\"*\" frameborder=\"no\" border=\"0\" framespacing=\"0\">\r\n");
      out.write("  <frame src=\"/system/top?menuid=109\" name=\"topFrame\" scrolling=\"No\" noresize=\"noresize\" id=\"topFrame\" title=\"topFrame\" />\r\n");
      out.write("  <frameset cols=\"198,*\" frameborder=\"no\" border=\"0\" framespacing=\"0\" id=\"fr\">\r\n");
      out.write("    <frame src=\"/system/left\" name=\"leftFrame\" scrolling=\"no\" id=\"leftFrame\" title=\"leftFrame\" />\r\n");
      out.write("    <frame src=\"/system/index\" name=\"mainFrame\" id=\"mainFrame\" title=\"mainFrame\" />\r\n");
      out.write("  </frameset>\r\n");
      out.write("  <frame src=\"/system/floor\" name=\"bottomFrame\" scrolling=\"No\" noresize=\"noresize\" id=\"bottomFrame\" title=\"bottomFrame\" />\t\r\n");
      out.write("</frameset>\r\n");
      out.write("<noframes>\r\n");
      out.write("</noframes> -->\r\n");
      out.write("<body  onLoad=\"onloalrame()\" style=\" margin:0px; padding:0px;\">\r\n");
      out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td colspan=\"2\">\r\n");
      out.write("    \t<iframe width=\"100%\" height=\"85px\" src=\"/system/top?menuid=109\" name=\"topFrame\" noresize=\"noresize\" id=\"topFrame\"  scrolling=\"no\" frameborder=\"0\" allowtransparency=\"true\"></iframe>\r\n");
      out.write("    </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td rowspan=\"2\" width=\"180\" valign=\"top\" style=\"background:url(../images/systems/leftbg.jpg) repeat-y;\">\r\n");
      out.write("    \t<iframe width=\"180px;\" height=\"100%\" src=\"/system/left\" frameborder=\"0\" allowtransparency=\"true\" name=\"leftFrame\" id=\"leftFrame\"  title=\"leftFrame\"></iframe>\r\n");
      out.write("    </td>\r\n");
      out.write("    <td>\r\n");
      out.write("    \t<iframe width=\"100%\" height=\"100%\" src=\"/system/index\" frameborder=\"0\" allowtransparency=\"true\"  name=\"mainFrame\" id=\"mainFrame\" title=\"mainFrame\" ></iframe>\r\n");
      out.write("    </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("</table>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
