Kasus ini adalah percobaan mengklasifikasikan 2 daun yaitu Pubescent Bamboo dan Chinese Horse Chestnut
dengan menggunakan fitur hanya Geometri saja. (fitur morfologi belum dihitung)

Fitur geometri yang digunakan:
1. Length
2. Diameter
3. Perimeter
4. Area

Percobaan menggunakan library WEKA dengan setting sebagai berikut:
1. Klasifikasi menggunakan SVM package SMO dengan kernel RBF, nilai gamma (sigma) = 0.01.
2. Data test yg digunakan untuk class Pubescent Bamboo adalah 10 buah dan data test untuk
   chinese horse chestnut berjumlah 11 buah.

Akurasi yg didapat adalah 95.2381 %. (untuk 2 class yg diuji)