# -*- coding: utf-8 -*-

from PyQt4.QtGui import QWidget


class Window(QWidget):
    def __init__(self, base, title):
        QWidget.__init__(self)
        self.setWindowTitle(title)
        self.base = base

    def __center_on_parent(self):
        geo = self.base.geometry()
        cx = geo.x() + (geo.width() / 2)
        cy = geo.y() + (geo.height() / 2)
        geo2 = self.geometry()
        fx = cx - (geo2.width() / 2)
        fy = cy - (geo2.height() / 2)
        self.setGeometry(fx,fy, geo2.width(), geo2.height())

    def keyPressEvent(self, event):
        if event.key() == Qt.Key_Escape:
            self.close()

    def show(self):
        QWidget.show(self)
        self.__center_on_parent()
