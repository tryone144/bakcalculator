package de.busse_apps.bakcalculator.common;

public class Calculator {

	public static int SEX_MALE = 1;
	public static int SEX_FEMALE = 0;
	
	// Berechnungsfaktoren
	private int weight;
	private int height;
	private int age;
	private int sex;
	
	private double volume_drunk;
	private double percent_alcohol;
	
	// Ergebnisse
	private double volume_alcohol;
	private double body_water_content;
	private double blood_distribution_factor;
	private double blood_alcohol_content;
	private double time;
	
	private double rho_alcohol = 0.8;
	private double rho_blood = 1.055;
	
	private double calculate_volumeAlcohol() {
		/* Berechnung des absoluten Alkoholvolumen im Körper
		 * Dazu wird das getrunkene Volumen und der Volumenanteil an Alkohol benötigt
		 * 
		 * A = V * e * p
		 * A: absolutes Alkoholvolumen in Liter (l)
		 * V: getrunkenes Volumen in Liter (l)
		 * e: Volumenanteil an Alkohol in Prozent (%)
		 * p: Dichte von Alkohol in Kilogramm / Liter (kg/l)
		 */
		
		return 1.0;
	}
	
	private double calculate_bodywatercontent() {
		/* Berechnung des Gesamtkörperwassers abhängig vom Geschlecht
		 * Dazu wird das Alter, die Körpergröße und das Körpergewicht benötigt
		 * 
		 * GKW(m) = 2,447 - 0,09516 * t + 0,1074 * h + 0,3362 * m
		 * GKW(w) = 0,203 - 0,07 * t + 0,1069 * h + 0,2466 * m
		 * GKW: Gesamtkörperwasser (männlich/weiblich)
		 * t: Alter in Jahren (y)
		 * h: Körpergröße in Zentimetern (cm)
		 * m: Körpergewicht in Kilogramm (kg)
		 */
		return 1.0;
	}
	
	private double calculate_bloodDistributionFactor() {
		/* Berechnung des Blutverteilungsfaktors
		 * Dazu wird die Menge des Gesamtkörperwassers und das Körpergewicht benötigt
		 * 
		 * r = (p * GKW) / (0,8 * m)
		 * r: Blutverteilungsfaktor
		 * p: Dichte von Blut in Kilogramm / Liter (kg/l)
		 * GKW: Gesamtkörperwasser
		 * m: Körpergewicht in Kilogramm (kg) 
		 */
		return 1.0;
	}
	
	private double calculate_bloodAlcoholContent() {
		/* Berechnung des Blutalkoholanteils
		 * Dazu wird das getrunkene Alkoholvolumen, das Körpergewicht und der Blutverteilungsfaktor benötigt
		 * Es werden 10% abgezogen, da der Alkohol nicht vollständig aufgenommen wird
		 * 
		 * c = A / (m * r)
		 * c: Blutalkoholkonzentration
		 * A: getrunkenes Alkoholvolumen in Liter (l)
		 * m: Körpergewicht in Kilogramm (kg)
		 * r: Blutverteilungsfaktor
		 */
		return 1.0;
	}
	
	private double calculate_time() {
		/* Berechnung der Zeit, die vergeht, bis der Alkohol nicht mehr im Körper nachweisbar ist
		 * Dabei gilt eine durchschnittliche Abbaukonstante von ca. 0,15 Promille (%.)
		 */
		return 1.0;
	}

	public void calculate() {
		/*
		 * Hauptmethode zum Berechnen, wird von außen aufgerufen
		 */
	}
	
	public void set_weight(int weight) {
		this.weight = weight;
	}
	
	public void set_height(int height) {
		this.height = height;
	}
	
	public void set_age(int age) {
		this.age = age;
	}
	
	public void set_sex(int sex) {
		this.sex = sex;
	}
	
	public void set_volume_drunk(double volume) {
		this.volume_drunk = volume;
	}
	
	public void set_percent_alcohol(double percent) {
		this.percent_alcohol = percent;
	}
}
