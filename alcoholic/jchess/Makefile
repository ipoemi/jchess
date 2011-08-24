.SUFFIXES: .java .class

LIB_PATH = lib/linux/
CLASS_PATH = ../../:lib/jvm/lwjgl.jar:lib/jvm/lwjgl_devil.jar:lib/jvm/lwjgl_fmod3.jar:lib/jvm/lwjgl_util.jar:lib/jvm/jinput.jar

JSRCS1_PATH = core
JSRCS2_PATH = scene
JSRCS3_PATH = network
JSRCS4_PATH = model

JSRCS1 = $(wildcard $(JSRCS1_PATH)/*.java)
JSRCS2 = $(wildcard $(JSRCS2_PATH)/*.java)
JSRCS3 = $(wildcard $(JSRCS3_PATH)/*.java)
JSRCS4 = $(wildcard $(JSRCS4_PATH)/*.java)

MAIN_SRC = JChess.java
TARGET = $(MAIN_SRC:.java=.class)

%.class : %.java
	javac -cp $$CLASSPATH:$(CLASS_PATH) $<

all : $(TARGET)

$(TARGET) : $(MAIN_SRC) $(JSRCS1) $(JSRCS2) $(JSRCS3) $(JSRCS4)
	$(MAKE) -C $(JSRCS1_PATH)/
	$(MAKE) -C $(JSRCS2_PATH)/
	$(MAKE) -C $(JSRCS3_PATH)/
	$(MAKE) -C $(JSRCS4_PATH)/
	javac -cp $$CLASSPATH:$(CLASS_PATH) $<

tag :
	rm tags || ctags -R *

run : $(TARGET)
	java -Djava.library.path=$(LIB_PATH) -cp $$CLASSPATH:$(CLASS_PATH) alcoholic.jchess.JChess

debug :
	javac -cp $$CLASSPATH:$(CLASS_PATH) JChessDebug.java
	java -Djava.library.path=$(LIB_PATH) -cp $$CLASSPATH:$(CLASS_PATH) alcoholic.jchess.JChessDebug

