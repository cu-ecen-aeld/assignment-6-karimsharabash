#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-karimsharabash.git;protocol=ssh;branch=main"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "0984055d38c440a2a3d0a860ce3d0992829ecb0e"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_DIR}"


inherit module update-rc.d

# Add your init script to SRC_URI
SRC_URI += " file://aesdchar-init"

# Define variables for update-rc.d framework
INITSCRIPT_PACKAGES = "${PN}"
# Use 'aesdchar.init' here to match the filename you're installing
INITSCRIPT_NAME:${PN} = "aesdchar-init"


FILES:${PN} += "${base_bindir}/aesdchar_load"
FILES:${PN} += "${base_bindir}/aesdchar_unload"
#FILES:${PN} += "/aesdchar.ko"
FILES_${PN} += "aesdchar-init"
FILES:${PN} += "${sysconfdir}/*"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
    install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    install -m 0755 ${WORKDIR}/aesdchar-init ${D}${sysconfdir}/init.d/
    install -d ${D}${base_bindir}
    install -m 0755 ${S}/aesdchar_load ${D}${base_bindir}/
    install -m 0755 ${S}/aesdchar_unload ${D}${base_bindir}/
}


