/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.54
 * Generated at: 2015-10-14 03:00:41 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.pages.system.company;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class companylogo_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/tags/ipav.tld", Long.valueOf(1444725534827L));
    _jspx_dependants.put("/pages/system/common/head.jsp", Long.valueOf(1427537646000L));
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
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
      out.write("<title>系统管理</title>\r\n");
      out.write("<script type=\"text/javascript\" src=\"/pages/js/jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tfunction initCut(){\r\n");
      out.write("\t\t$(\"#x\").val(\"0\");\r\n");
      out.write("\t\t$(\"#y\").val(\"0\");\r\n");
      out.write("\t\t$(\"#width\").val(\"410\");\r\n");
      out.write("\t\t$(\"#height\").val(\"213\");\r\n");
      out.write("\t\t$(\"#txtWidth\").html(\"135\");\r\n");
      out.write("\t    $(\"#txtHeight\").html(\"45\");\r\n");
      out.write("\t\t$(\"#filefield\").uploadPreview({\r\n");
      out.write("\t\t\twidth : 410,\r\n");
      out.write("\t\t\theight : 213,\r\n");
      out.write("\t\t\timgPreview : \"#img1\",\r\n");
      out.write("\t\t\timgCutview:\"#newimg\",\r\n");
      out.write("\t\t\timgType : [ \"png\", \"jpg\" ],\r\n");
      out.write("\t\t\tcallback : function() {\r\n");
      out.write("\t\t\t\treturn false;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t$('#img1').imgAreaSelect({ \r\n");
      out.write("\t\t\tselectionColor: 'blue', \r\n");
      out.write("\t\t\tmaxWidth:410,\r\n");
      out.write("\t\t\tminWidth:0,\r\n");
      out.write("\t\t\tminHeight:0,\r\n");
      out.write("\t\t\tmaxHeight:213, \r\n");
      out.write("\t\t\tselectionOpacity: 0.2,\r\n");
      out.write("\t\t\taspectRatio: '3:1',//设定选取区域的显示比率，如：”4:3“\r\n");
      out.write("\t\t\tinstance:true,//若设为true，imgAreaSelect()函数会返回一个对选择区域图像的一个引用，以便能够进一步使用API。（详见８、API方法）\r\n");
      out.write("\t\t\tautoHide:true,//如果设为true，那么在选择完后区域会消失。 \r\n");
      out.write("\t\t\tonSelectChange: processImg \r\n");
      out.write("\t\t}); \r\n");
      out.write("\t}\r\n");
      out.write("\tfunction clearImage(){\r\n");
      out.write("\t\t$(\"#img1\").removeAttr(\"src\");\r\n");
      out.write("\t\t$(\"#newimg\").removeAttr(\"src\");\r\n");
      out.write("\t\t//清空file文件\r\n");
      out.write("\t\tvar file = $(\"#filefield\");\r\n");
      out.write("\t\tfile.after(file.clone().val(\"\"));\r\n");
      out.write("\t\tfile.remove();\r\n");
      out.write("\t\t//初始化函数绑定和数据\r\n");
      out.write("\t\tinitCut();\r\n");
      out.write("\t}\r\n");
      out.write("// \tfunction showCutImage(){\r\n");
      out.write("// \t\t$('#img1').imgAreaSelect({ \r\n");
      out.write("// \t\t\tselectionColor: 'blue', \r\n");
      out.write("// \t\t\tmaxWidth:410,\r\n");
      out.write("// \t\t\tminWidth:0,\r\n");
      out.write("// \t\t\tminHeight:0,\r\n");
      out.write("// \t\t\tmaxHeight:213, \r\n");
      out.write("// \t\t\tselectionOpacity: 0.2,\r\n");
      out.write("// \t\t\taspectRatio: '3:1',//设定选取区域的显示比率，如：”4:3“\r\n");
      out.write("// \t\t\tinstance:true,//若设为true，imgAreaSelect()函数会返回一个对选择区域图像的一个引用，以便能够进一步使用API。（详见８、API方法）\r\n");
      out.write("// \t\t\tautoHide:true,//如果设为true，那么在选择完后区域会消失。 \r\n");
      out.write("// \t\t\tonSelectChange: processImg \r\n");
      out.write("// \t\t}); \r\n");
      out.write("// \t}\r\n");
      out.write("\tfunction validata(){\r\n");
      out.write("\t\tif(isNotNull($(\"#img1\").attr(\"src\"))&&isNotNull($(\"#newimg\").attr(\"src\"))){\r\n");
      out.write("\t\t\tlogoForm.submit();\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tshowMsg(\"请选择图片作为LOGO!\");\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("    function processImg(img, selection){\r\n");
      out.write("    \t//为了保证在预览框内有剪切后的图片存在的情况下,保证重新剪切的效果\r\n");
      out.write("    \tif(isNotNull($(\"#hidsource\").val())){\r\n");
      out.write("    \t\t$(\"#newimg\").attr(\"src\",$(\"#img1\").attr(\"src\"));\r\n");
      out.write("    \t\t$(\"#hidsource\").val(\"\");\r\n");
      out.write("    \t}    \r\n");
      out.write("\t\tvar scaleX = 135 / (selection.width || 1);\r\n");
      out.write("\t    var scaleY = 45 / (selection.height || 1);\r\n");
      out.write("\t    $(\"#x\").val(Math.round(selection.x1));\r\n");
      out.write("\t    $(\"#y\").val(Math.round(selection.y1));\r\n");
      out.write("\t    var select_width=Math.round(selection.width);\r\n");
      out.write("\t    var select_height=Math.round(selection.height);\r\n");
      out.write("\t    $(\"#width\").val(select_width);\r\n");
      out.write("\t    $(\"#height\").val(select_height);\r\n");
      out.write("\t    $(\"#txtWidth\").html(select_width);\r\n");
      out.write("\t    $(\"#txtHeight\").html(select_height);\r\n");
      out.write("\t    $('#newimg').css({\r\n");
      out.write("\t        width: Math.round(scaleX * 410) + 'px',\r\n");
      out.write("\t        height: Math.round(scaleY * 213) + 'px',\r\n");
      out.write("\t        marginLeft: '-' + Math.round(scaleX * selection.x1) + 'px',\r\n");
      out.write("\t        marginTop: '-' + Math.round(scaleY * selection.y1) + 'px'\r\n");
      out.write("\t    });\r\n");
      out.write("\t}\r\n");
      out.write("    function showMsg(msg){\r\n");
      out.write("    \t$(\"#msg_info\").html(msg);\r\n");
      out.write("\t\t$(\"#simScrollCont\").show();\r\n");
      out.write("\t\t$(\"#simScrollContBox\").show();\r\n");
      out.write("    }\r\n");
      out.write("\tfunction closeMsg(){\r\n");
      out.write("    \t$(\"#simScrollCont\").hide();\r\n");
      out.write("    \t$(\"#simScrollContBox\").hide();\r\n");
      out.write("    }\r\n");
      out.write(" \t //根据窗口大小调整弹出框的位置\r\n");
      out.write("    $(window).resize(function() {\r\n");
      out.write("    \t$(\".simScrollContBox4\").css({\r\n");
      out.write("            position: \"absolute\",\r\n");
      out.write("            left: ($(window).width() - $(\".simScrollContBox4\").outerWidth()) / 2,\r\n");
      out.write("            top: ($(window).height() - $(\".simScrollContBox4\").outerHeight()) / 2\r\n");
      out.write("        });\r\n");
      out.write("    })\r\n");
      out.write("    \t\r\n");
      out.write("\t$(function() {\r\n");
      out.write("\t\t$(window).resize();\r\n");
      out.write("\t\t//初始化遮罩层高度\r\n");
      out.write("\t\t$(\"#simScrollCont\").css(\"height\",$(\"#mainFrame\",window.parent.document).height());\r\n");
      out.write("\t\tvar message=$(\"#hid_save_message\").val();\r\n");
      out.write("\t\tif(isNotNull(message)){\r\n");
      out.write("\t\t\tvar info=\"保存企业LOGO失败!\";\r\n");
      out.write("    \t\tif(message==\"success\") \tinfo=\"保存企业LOGO成功!请点击公司LOGO刷新数据!\";\r\n");
      out.write("\t\t\tshowMsg(info);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tinitCut();\r\n");
      out.write("\t\t\r\n");
      out.write("\t});  \r\n");
      out.write("  </script>\r\n");
      out.write("  <style type=\"text/css\">\r\n");
      out.write(".file-box{ position:relative;width:340px}\r\n");
      out.write(".btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<input type=\"hidden\" id=\"hid_save_message\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${message }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\"/>\r\n");
      out.write("<form action=\"/saveLogo\" method=\"post\" enctype=\"multipart/form-data\" name=\"logoForm\">\r\n");
      out.write("<input type=\"hidden\" name=\"x\" id=\"x\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" name=\"y\" id=\"y\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" name=\"width\" id=\"width\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" name=\"height\" id=\"height\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" name=\"companyid\"  value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${curuser.companyid}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" id=\"hidsource\"  value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sourceimage}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\">\r\n");
      out.write("<div class=\"centmenudiv\">\r\n");
      out.write("\t<ol class=\"centmenu\" id=\"centmenu\">\r\n");
      out.write("    \t<li><a href=\"#1\" onClick=\"set_menu3(0)\" class=\"current\">企业LOGO</a></li>\r\n");
      out.write("    </ol>\r\n");
      out.write("</div>\r\n");
      out.write("<table style=\"margin-left:88px; margin-top:10px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("  <tr height=\"100\">\r\n");
      out.write("    <td valign=\"middle\" colspan=\"2\">\r\n");
      out.write("    \t<p style=\" vertical-align:middle;  line-height:40px;\">\r\n");
      out.write("    \t<input type=\"button\"  value=\"选择企业LOGO\"    class=\"buth120 fl\"  >\r\n");
      out.write("    \t<input type=\"file\" name=\"filefield\" id=\"filefield\" value=\"选择企业LOGO\" style=\" height:40px; width:120px; float:left;position:absolute;\r\n");
      out.write("\t\t\tmargin-left:-120px; filter:alpha(opacity:0);opacity: 0;\">&nbsp;&nbsp;上传企业LOGO，支持jpg、png格式的图片</p> \r\n");
      out.write("    </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td width=\"450\">\r\n");
      out.write("    \t<div class=\"qylogo\" align=\"center\" id=\"logoview\">\r\n");
      out.write("    \t\t<img id=\"img1\" style=\"width:410px;\" ");
      if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
        return;
      out.write("  />\r\n");
      out.write("    \t</div>\r\n");
      out.write("    </td>\r\n");
      out.write("    <td valign=\"top\">\r\n");
      out.write("    \t<p>LOGO预览</p>\r\n");
      out.write("    \t<div class=\"qylogo2\" id=\"preview\" style=\"width:135px; height:45px;overflow:hidden; border:1px solid gray;\">\r\n");
      out.write("    \t\t<img id=\"newimg\" style=\"width:135px; height:45px;\"  ");
      if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
        return;
      out.write("  />\r\n");
      out.write("    \t</div> \r\n");
      out.write("\t\t<label>宽  <span id=\"txtWidth\"></span>* 高 <span id=\"txtHeight\"></span></label>\r\n");
      out.write("\t\t \r\n");
      out.write("    </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("  <tr height=\"140\">\r\n");
      out.write("    <td>\r\n");
      out.write("    \t<input type=\"button\" value=\"保存企业LOGO\" class=\"butl120 bcqy1\" onmouseover=\"this.className='butl120m bcqy1'\" \r\n");
      out.write("    \tonmouseout=\"this.className='butl120 bcqy1'\" onclick=\"validata()\">\r\n");
      out.write("        <input type=\"button\" value=\"清空选择\" class=\"buth100\" onmouseover=\"this.className='buth100m'\" \r\n");
      out.write("        onmouseout=\"this.className='buth100'\" onclick=\"clearImage()\">\r\n");
      out.write("    </td>\r\n");
      out.write("    <td></td>\r\n");
      out.write("  </tr>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
      out.write("<!-- 提示信息 -->\r\n");
      out.write("<div id=\"simScrollCont\" class=\"simScrollCont\"></div>\r\n");
      out.write("<div class=\"simScrollContBox4\" id=\"simScrollContBox\">\r\n");
      out.write("    \t<div class=\"simScrollContBoxs4\">\r\n");
      out.write("        <div class=\"tctitle\">提示信息<a href=\"javascript:;\" onClick=\"closeMsg()\" title=\"关闭\"></a></div>\r\n");
      out.write("        \r\n");
      out.write("      <div class=\"ptx\"></div>\r\n");
      out.write("        <div id=\"tbBoxgdt2\" class=\"alertBox\">\r\n");
      out.write("        \t<p class=\"alertmsg\" id=\"msg_info\"></p>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"alertbut\">\r\n");
      out.write("        \t<input type=\"button\" value=\"确定\" class=\"yes fr mr20\" onClick=\"closeMsg()\" />\r\n");
      out.write("        </div>\r\n");
      out.write("        </div>\r\n");
      out.write("</div> \r\n");
      out.write("</body>\r\n");
      out.write("</html>");
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

  private boolean _jspx_meth_c_005fif_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent(null);
    // /pages/system/company/companylogo.jsp(159,42) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sourceimage ne ''}", java.lang.Boolean.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("src=\"");
        if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
          return true;
        out.write('"');
        int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fout_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_c_005fout_005f0 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fout_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f0);
    // /pages/system/company/companylogo.jsp(159,81) name = value type = null reqTime = true required = true fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_c_005fout_005f0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sourceimage }", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
    int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
    if (_jspx_th_c_005fout_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f1 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f1.setParent(null);
    // /pages/system/company/companylogo.jsp(165,58) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_c_005fif_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${subimage ne ''}", java.lang.Boolean.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
    if (_jspx_eval_c_005fif_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write(" src=\"");
        if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
          return true;
        out.write('"');
        int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005fout_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f1, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_c_005fout_005f1 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fout_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f1);
    // /pages/system/company/companylogo.jsp(165,95) name = value type = null reqTime = true required = true fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_c_005fout_005f1.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${subimage }", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
    int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
    if (_jspx_th_c_005fout_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
    return false;
  }
}
