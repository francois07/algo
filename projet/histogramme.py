# -*- coding: latin-1 -*-
# 
""" 
Ouvrir un terminal, et se placer dans le répertoire qui contient
vos fichiers valeursSGD.csv    et    valeursSGV.csv générés par votre programme.
Taper la commande    
python histogramme.py (python 2) ou python3 histogramme.py (python 3).
Vous obtenez une image  valeursSGD.png  et une image  valeursSGD.png 
contenant l'histogramme des valeurs des sacs "gloutons par densité" 
et "gloutons par densité".
"""
import csv
import matplotlib.pyplot as plt
from numpy import median, mean
def histogramme(fileName) : # fileName = "valeursSGD" ou "valeursSGV"
	VSG = [] # valeurs des sacs "gloutons
	with open(fileName + ".csv") as csvfile:
		reader = csv.reader(csvfile)
		for row in reader:
			VSG.append(int(row[0]))
		h = plt.hist(VSG,bins=100)
		plt.savefig(fileName+".png")
		plt.close()
		return median(VSG), mean(VSG)
	csvfile.close

histogramme("valeursSGD")
histogramme("valeursSGV")









