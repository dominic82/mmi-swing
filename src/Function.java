/*
 * Coderahmen fuer die Mensch-Maschine-Interaktion-Uebungen
 * TU Dortmund - Lehrstuhl Informatik VII
 * 
 */

import java.util.*;

/**
 * Function ist ein Wrapper fuer einen mathematische Funktionsbeschreibung
 * Ueber diese Klassen kann eine als String gegebenen Funktion ausgewertet werden.
 */
public class Function extends Observable{
	private String formula;
	private Expression root = null;
	
	/**
	 * Erzeugt ein neues Funktionsobjekt
	 * @param formula muss ein gueltiger Funktionsausdruck sein
	 * @throws IllegalArgumentException wird geworfen wenn der Funktionsausdruck nicht verarbeitet werden kann
	 */
	public Function(String formula) throws IllegalArgumentException {
		setFunction(formula);
	}
	
	/**
	 * Setzt den Funktionsausdruck neu
	 * Wenn der neue Funktionsausdruck nicht verarbeitet werden kann,
	 * wir das die Funktion nicht veraendert.
	 * @param formula muss ein gueltiger Funktionsausdruck sein
	 * @throws IllegalArgumentException wird geworfen wenn der Funktionsausdruck nicht verarbeitet werden kann
	*/
	public void setFunction(String formula) throws IllegalArgumentException {
		int[] position = new int[] {0};
		root = parse(formula, position);
		this.formula = formula;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Berechnet den Funktionswert zu einer gegebenen Stelle x
	 * @param x irgendein Wert fuer den Parameter x.
	 * @return f(x) fuer die gegebene Funktion
	 */
	public double valueAt(double x) {
		return root.calc(x);
	}
	
	/**
	 * @return the formula
	 */
	public String toString() {
		return new String(formula);
	}
	
	private Expression parse(String formula, int[] position) throws IllegalArgumentException {
		if (formula.length() == 0) return new Constant(Double.NaN);
		Expression root = null;;
		Expression lastExpression = null;
		while(position[0] < formula.length()) {
			Func negFunc = null;
			char c = formula.charAt(position[0]);
			if(Character.isWhitespace(c)) continue;
			Expression currentExpression = null;
        	if( c == '(') {
	        	position[0]++;
	        	currentExpression = parse(formula, position);
	        } else if (c == ')') {
	        	if (lastExpression == null) {
	        		throw new IllegalArgumentException("Missing Statement at "+position);
	        	} else if (lastExpression instanceof Operation && ((Operation)lastExpression).getRightExpression() == null){
	        		throw new IllegalArgumentException("Missing operand at "+position[0]);
	        	} else {
	        		position[0]++;
	        		return lastExpression;
	        	}
	        } else if (c == '+' && lastExpression == null) {
        		position[0]++;
        	} else if (c == '-' && lastExpression == null) {
        		negFunc = new Func() { public double calc(double x) { return -argument.calc(x);} public String toString() { return "-"+argument; }};
        		position[0]++;
        		c = formula.charAt(position[0]);
        	}
        	if (Character.isDigit(c) || c == '.') {
	        	// Next expression is a number
	            int start = position[0];
	        	do {
	        		position[0]++;
		        } while (position[0] < formula.length() && (Character.isDigit(formula.charAt(position[0])) || formula.charAt(position[0]) == '.'));
	        	try {
	        		currentExpression = new Constant(Double.parseDouble(formula.substring(start, position[0])));
	        	} catch (NumberFormatException e) {
	        		throw new NumberFormatException("Cannot read number at "+start);
	        	}
	        } else if (Character.isLetter(c)) {
        		// constant or x?
        		int start = position[0];
        		int end = position[0];
        		while (end < formula.length() && Character.isLetter(formula.charAt(end))) {end++;}
        		String token = formula.substring(start, end);
        		if (token.equalsIgnoreCase("x")) {
        			currentExpression = new Expression() { public double calc(double x) { return x; }  public String toString() { return "x"; }};
        			position[0]=end;
        		} else if (token.equalsIgnoreCase("e")) {
        			currentExpression = new Constant(Math.E);
        			position[0]=end;
        		} else if (token.equalsIgnoreCase("pi")) {
        			currentExpression = new Constant(Math.PI);
        			position[0]=end;
        		}
        	}
        	if (currentExpression == null) { 
	        	if (lastExpression == null || (lastExpression instanceof Operation && ((Operation)lastExpression).getRightExpression() == null)) {
	        		// Function
	        		Func function;
	        		int start = position[0];
	        		while (Character.isLetter(c)) {
	        			position[0]++;
	        			if (position[0] >= formula.length()) throw new IllegalArgumentException("Function without argument? Error at "+position[0]);
	        			c = formula.charAt(position[0]);
	        		}
	        		String func = formula.substring(start, position[0]);
	        		if (func.equalsIgnoreCase("sin")) {
	        			function = new Func() { public double calc(double x) { return Math.sin(argument.calc(x)); } public String toString() { return "sin("+argument+")"; }};
	        		} else if (func.equalsIgnoreCase("cos")) {
	        			function = new Func() { public double calc(double x) { return Math.cos(argument.calc(x)); } public String toString() { return "cos("+argument+")"; }};
	        		} else if (func.equalsIgnoreCase("tan")) {
	        			function = new Func() { public double calc(double x) { return Math.tan(argument.calc(x)); } public String toString() { return "tan("+argument+")"; }};
	        		} else if (func.equalsIgnoreCase("asin")) {
	        			function = new Func() { public double calc(double x) { return Math.asin(argument.calc(x)); } public String toString() { return "asin("+argument+")"; }};
	        		} else if (func.equalsIgnoreCase("acos")) {
	        			function = new Func() { public double calc(double x) { return Math.acos(argument.calc(x)); } public String toString() { return "acos("+argument+")"; }};
	        		} else if (func.equalsIgnoreCase("atan")) {
	        			function = new Func() { public double calc(double x) { return Math.atan(argument.calc(x)); } public String toString() { return "atan("+argument+")"; }};
	        		} else if (func.equalsIgnoreCase("exp")) {
	        			function = new Func() { public double calc(double x) { return Math.exp(argument.calc(x)); } public String toString() { return "exp("+argument+")"; }};
	        		} else if (func.equalsIgnoreCase("log")) {
	        			function = new Func() { public double calc(double x) { return Math.log(argument.calc(x)); } public String toString() { return "log("+argument+")"; }};
	        		} else if (func.equalsIgnoreCase("sqrt")) {
	        			function = new Func() { public double calc(double x) { return Math.sqrt(argument.calc(x)); } public String toString() { return "sqrt("+argument+")"; } };
	        		} else {
	        			throw new IllegalArgumentException("Function unsupported. Error at "+start);
	        		}
	        		if (formula.charAt(position[0]) == '(') {
	        			position[0]++;
	        			function.setArgument(parse(formula, position));
	        			currentExpression = function;
	        		} else {
	        			throw new IllegalArgumentException("Function arguments must be set in parenthesis. Error at "+position[0]);
	        		}
	        		if (root == null) root = lastExpression;
	        	} else {
	        		// next should be Operator
	        		Operation operation;
	        		switch(c) {
	        			case '+':
	        				operation = new Operation() {
	        					public int getPriority() { return 1; }
	        					public double calc(double x) { return left.calc(x) + right.calc(x); }
	        					public String toString() { return "("+left+"+"+right+")"; }
	        				};
	        				break;
	        			case '-':
	        				operation = new Operation() {
	        					public int getPriority() { return 1; }
	        					public double calc(double x) { return left.calc(x) - right.calc(x); }
	        					public String toString() { return "("+left+"-"+right+")"; }
	        				};
	        				break;
	        			case '*':
	        				operation = new Operation() {
	        					public int getPriority() { return 2; }
	        					public double calc(double x) { return left.calc(x) * right.calc(x); }
	        					public String toString() { return "("+left+"*"+right+")"; }
	        				};
	        				break;
	        			case '/':
	        				operation = new Operation() {
	        					public int getPriority() { return 2; }
	        					public double calc(double x) { return left.calc(x) / right.calc(x); }
	        					public String toString() { return "("+left+"/"+right+")"; }
	        				};
	        				break;
	        			case '^':
	        				operation = new Operation() {
	        					public int getPriority() { return 3; }
	        					public double calc(double x) { return Math.pow(left.calc(x),right.calc(x)); }
	        					public String toString() { return "("+left+"^"+right+")"; }
	        				};
	        				break;
	        			
	        			default:
	        				throw new IllegalArgumentException("Unsupported Operation at "+position[0]);
	        		}
	        		if (lastExpression instanceof Operation) {
	        			Operation last = (Operation)lastExpression;
	        			if (last.getPriority() < operation.getPriority()) {
	        				operation.setLeftExpression(last.getRightExpression());
	        				last.setRightExpression(operation);
	        				lastExpression = operation;
	        			} else {
	        				operation.setLeftExpression(root);
	        				root = lastExpression = operation;
	        			}
	        		} else {
	        			operation.setLeftExpression(lastExpression);
	        			root = lastExpression = operation;
	        		}
	        		position[0]++;
	        	}
        	}
        	if (currentExpression != null) {
        		if (negFunc != null) {
        			negFunc.setArgument(currentExpression);
        			currentExpression = negFunc;
        		}
        		if (lastExpression == null) {
    				root = lastExpression = currentExpression;
    			} else if (lastExpression instanceof Operation && ((Operation)lastExpression).getRightExpression() == null) {
    				((Operation)lastExpression).setRightExpression(currentExpression);
    			} else {
    				throw new IllegalArgumentException("Missplaced constant or x at "+position[0]);
    			}
    		}
        }
		if (root == null || (root instanceof Operation && ((Operation)root).getRightExpression() == null)) {
			throw new IllegalArgumentException("Incomplete formula!");
		}
		return root;
	}
	
	private interface Expression {
		public double calc(double x);
	}
	
	private abstract class Operation implements Expression {
		public abstract int getPriority();
		public void setLeftExpression(Expression expression) { left = expression; }
		public void setRightExpression(Expression expression) {	right = expression; }
		public Expression getRightExpression() { return right; }
		
		protected Expression left=null, right=null;
	}
	
	private abstract class Func implements Expression {
		public void setArgument(Expression expression) { argument = expression; }
		protected Expression argument;
	}
	
	private class Constant implements Expression {
		public Constant(double value) { this.value = value; }
		public double calc(double x) { return value; }
		public String toString() { return String.valueOf(value); }
		private double value;
	}
}