#
#  Makefile for ARM XINU.

BOOTIMAGE = xinu.boot
KERNARC   = xinu/kernel.a

TEMPLATE_ARCH := arm
PLATFORM = arm-rpi
# Architecture root and prefix (ignored if user overrides COMPILER_ROOT from the
# toplevel Makefile).
ARCH_ROOT     :=
ARCH_PREFIX   := arm-none-eabi-
COMPILER_ROOT := $(ARCH_ROOT)$(ARCH_PREFIX)
LDLIBS :=
LDLIBS  := -lgcc
# Set the actual compiler and several binutils programs.
CC       := $(COMPILER_ROOT)gcc
AR       := $(COMPILER_ROOT)ar
LD       := $(COMPILER_ROOT)ld
STRIP    := $(COMPILER_ROOT)strip
OBJCOPY  := $(COMPILER_ROOT)objcopy
DETAIL   = 


DEFS     =
ASFLAGS  := -mcpu=arm1176jzf-s -mtune=arm1176jzf-s -Ixinu
CFLAGS   := -mcpu=arm1176jzf-s -mtune=arm1176jzf-s -Ixinu -nostdlib

OCFLAGS  := -I binary -O elf32-littlearm -B arm 
LIB      = ../lib
#LDFLAGS   += -static --gc-sections -no-warn-mismatch -T xinu/ld.script
LDFLAGS   += -static -T xinu/ld.script

$(VERBOSE).SILENT:

#-----------------------------------------------------------------------
# make targets
#-----------------------------------------------------------------------
# Order matters here!  LDRASM (loader/start.S) *must* come first!

%.o: %.S
	@echo -e "\tAssembling" $@
	$(CC) $(CFLAGS) -c -o $@ $<

${BOOTIMAGE}: ${LDRASM:%.S=%.o} ${KERNARC} xinu/ld.script main.o xinu/libxc.a
	@echo -e "\tLinking" $@
	${CC} -Wl,--static -Wl,--gc-sections -Wl,-Txinu/ld.script -nostdlib -o xinu.elf main.o ${KERNARC} xinu/libxc.a ${KERNARC} xinu/main.o xinu/divide.o -lgcc
	$(OBJCOPY) -O binary xinu.elf xinu.boot

clean:
	rm -f *.o ${BOOTIMAGE} kernel.img xinu.elf
