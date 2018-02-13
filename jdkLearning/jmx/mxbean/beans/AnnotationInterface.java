package mxbean.beans;

import javax.management.MXBean;

@MXBean(true)
public interface AnnotationInterface {
int getBbt();
void setBbt(int bbt);
String getNote();
void setNote(String note);


String testPara(StandardMxBeanSample bean);
}
