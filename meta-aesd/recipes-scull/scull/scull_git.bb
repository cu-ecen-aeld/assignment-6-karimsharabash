# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-karimsharabash.git;protocol=ssh;branch=main"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "162a622f660a6e835560ae2359467eae4c1e397e"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"


inherit module update-rc.d

# Add your init script to SRC_URI
SRC_URI += " file://scull-init"

# Define variables for update-rc.d framework
INITSCRIPT_PACKAGES = "${PN}"
# Use 'scull.init' here to match the filename you're installing
INITSCRIPT_NAME:${PN} = "scull-init"


FILES:${PN} += "${base_bindir}/scull_load"
FILES:${PN} += "${base_bindir}/scull_unload"
#FILES:${PN} += "/scull.ko"
FILES_${PN} += "scull-init"
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
    install -m 0755 ${S}/scull/scull.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    install -m 0755 ${WORKDIR}/scull-init ${D}${sysconfdir}/init.d/
    install -d ${D}${base_bindir}
    install -m 0755 ${S}/scull/scull_load ${D}${base_bindir}/
    install -m 0755 ${S}/scull/scull_unload ${D}${base_bindir}/
}


