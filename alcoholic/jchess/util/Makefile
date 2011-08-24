.SUFFIXES: .java .class

LIB_PATH = lib/linux/
CLASS_PATH = ../../../:../lib/jvm/lwjgl.jar:../lib/jvm/lwjgl_devil.jar:../lib/jvm/lwjgl_fmod3.jar:../lib/jvm/lwjgl_util.jar:lib/jvm/jinput.jar

JSRCS = $(wildcard *.java)

TARGET = $(JSRCS:.java=.class)

%.class : %.java
	javac -cp $$CLASSPATH:$(CLASS_PATH) $<

all : $(TARGET)

run :
	$(MAKE) -C ../ run

tag :
	rm tags || ctags -R ../*
