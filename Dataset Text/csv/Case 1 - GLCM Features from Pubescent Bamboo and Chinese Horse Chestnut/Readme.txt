Kasus ini adalah percobaan mengklasifikasikan 2 daun yaitu Pubescent Bamboo dan Chinese Horse Chestnut
dengan menggunakan fitur hanya GLCM saja.

Fitur GLCM yang digunakan:
1. Angular Second Moment
2. Correlation
3. Entropy
4. Homogeneity
5. Contrast

Percobaan menggunakan library WEKA dengan setting sebagai berikut:
1. Klasifikasi menggunakan SVM package SMO dengan kernel RBF, nilai gamma (sigma) = 0.01.
2. Data test yg digunakan untuk class Pubescent Bamboo adalah 10 buah dan data test untuk
   chinese horse chestnut berjumlah 11 buah.

Akurasi yg didapat adalah 100 %. (untuk 2 class yg diuji)