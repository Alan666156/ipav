/**
 * 将带有年月日的日期转为YYYY-MM-DD格式
 * @param date
 * @returns
 */
function convertDate(date){
	if(/^\d[4]年\d[2]月\d[1]日$/.test(date))
		date=date.replace("年","-").replace("月","-0").replace("日","");
	else if(/^\d[4]年\d[1]月\d[2]$/.test(date))
		date=date.replace("年","-0").replace("月","-").replace("日","");
	else if(/^\d[4]年\d[1]月\d[1]$/.test(date)) 
		date=date.replace("年","-0").replace("月","-0").replace("日","");
	else 
		date=date.replace("年","-").replace("月","-").replace("日","");
	return date;
}
