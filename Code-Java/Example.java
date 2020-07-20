
//This is my example Solution
import java.lang.Math;
import java.util.*;

class Example {

	static long startTime;
	static Random ran = new Random();

	public static void main(String[] args) {
		// Do not delete/alter the next line
		long startT = System.currentTimeMillis();
		//

		startTime = startT;

		String name = "AD";
		String login = "ad";

		System.out.println("----------");
		System.out.println("PROGRAM INITIATED");
		System.out.println("MAX RUN TIME : 20 SECONDS");
		System.out.println("----------");
		double[] sol1 = solution1();

		boolean[] sol2 = solution2();

		Assess.checkIn(name, login, sol1, sol2);
		// Do not delete or alter the next line
		long endT = System.currentTimeMillis();
		System.out.println("Total execution time was: " + ((endT - startT) / 1000.0) + " seconds");
		//
		System.out.println("----------");
	}

	public static double[] solution1() {
		Population1 population = new Population1();
		//While loop to do breed functions and getFitness functions for a limited 10 seconds, and until fitness is not 0
		while ((population.bestFitness > 0) && (System.currentTimeMillis() - startTime < 10000.0)) {
			//breed function in population
			population.breed();
			//bestFitness function
			population.getBestFitness();
			//System.out.println(Arrays.toString(population.bestCandidate.solution));
		}
		//Returns best candidate as solution 1 answer
		return population.bestCandidate.solution;
	}

	static class Population1 {
		//Candidate Array of candidates at 100 values
		Candidate1[] candidates = new Candidate1[100];
		Candidate1 bestCandidate = new Candidate1();
		double bestFitness = Double.MAX_VALUE;

		public Population1() {
			for (int i = 0; i < candidates.length; i++) {
				Candidate1 c = new Candidate1();
				//randomise candidates
				c.randomise();
				//get candidate fitness
				c.getFitness();
				//add candidate fitnesses to candidates array
				candidates[i] = c;
			}
			//getBest fitness of candidate
			getBestFitness();
		}

		public void getBestFitness() {
			//Sort candidates best to worst
			Arrays.sort(candidates, Comparator.comparing(c -> c.fitness));
			//put best candidate into bestCandidate
			bestCandidate = candidates[0];
			bestFitness = bestCandidate.fitness;
		}

		public void breed() {
			//New Candidate Arrays, 100 values
			Candidate1[] nextGen = new Candidate1[100];
			//Automatically put nextGen 0 as best Candidate
			nextGen[0] = candidates[0];
			for (int i = 1; i < 7; i++) {
				Candidate1 child = new Candidate1();
				//random 7 candidates added to array
				child.randomise();
				child.getFitness();
				nextGen[i] = child;
			}
			for (int i = 7; i < nextGen.length; i += 2) {
				//rest of the candidates added to array
				Candidate1[] parents = runTournament();
				Candidate1[] children = mate(parents);
				nextGen[i] = children[0];
				if (i != 99) {
					nextGen[i + 1] = children[1];
				}
			}
			candidates = nextGen;
		}

		public Candidate1[] runTournament() {
			Candidate1[] tournament = new Candidate1[5];
			for (int i = 0; i < tournament.length; i++) {
				//put random candidates into tournament
				tournament[i] = candidates[ran.nextInt(100)];
			}
			//Sort tournament
			Arrays.sort(tournament, Comparator.comparing(c -> c.fitness));

			Candidate1[] parents = new Candidate1[2];

			parents[0] = tournament[0];
			parents[1] = tournament[1];
			return parents;
		}

		public Candidate1[] mate(Candidate1[] parents) {
			Candidate1 p1 = parents[0];
			Candidate1 p2 = parents[1];

			Candidate1 c1 = new Candidate1();
			Candidate1 c2 = new Candidate1();

			Candidate1[] offSpring = new Candidate1[2];

			int crossOverPoint = ran.nextInt(21);
			//Adding parent index to child index if i > crossOverPoint
			for (int i = 0; i < c1.solution.length; i++) {
				if (i >= crossOverPoint) {
					c1.solution[i] = p1.solution[i];
					c2.solution[i] = p2.solution[i];
				} else {
					c1.solution[i] = p2.solution[i];
					c2.solution[i] = p1.solution[i];
				}
			}

			//Mutate randomly using mutate method
			if (ran.nextDouble() > 0.7) {
				c1.mutate();
				c2.mutate();
			}

			
			//getFitness of child 1 and 2
			c1.getFitness();
			c2.getFitness();

			//put child 1 and 2 into offspring
			offSpring[0] = c1;
			offSpring[1] = c2;

			return offSpring;
		}

	}

	static class Candidate1 {
		double[] solution = new double[20];
		double fitness;

		public Candidate1() {
			this.fitness = Double.MAX_VALUE;
		}

		public void randomise() {
			//randomise solution between -5 and 5
			for (int i = 0; i < solution.length; i++) {
				solution[i] = -5 + (5 - (-5)) * ran.nextDouble();
			}
		}

		public void getFitness() {
			//get fitness of solution
			this.fitness = Assess.getTest1(this.solution);
		}

		public void mutate() {
			int mutation = ran.nextInt(5);
			//Random mutations happening through ran.nextInt
			if (mutation == 0) {
				for (int i = 0; i < solution.length; i++) {
					//multiplication & division mutation
					solution[i] *= 0.5 + (1.5 - (0.5)) * ran.nextDouble();
				}
			} else if (mutation == 1) {
				for (int i = 0; i < solution.length; i++) {
					//adding mutation
					solution[i] += 0.01 + (1.5 - (0.01)) * ran.nextDouble();
				}
			} else if (mutation == 2) {
				for (int i = 0; i < solution.length; i++) {
					//subtracting mutation
					solution[i] -= 0.01 + (1.5 - (0.01)) * ran.nextDouble();
				}
			} else if (mutation == 3) {
				//rounding mutation
				double decimals = ran.nextInt(15 + 1);
				double exponent = Math.pow(10, decimals);
				for (int i = 0; i < solution.length; i++) {
				solution[i] = Math.round(solution[i] * exponent)/exponent;
				}
			} else if (mutation == 4) {
				//averaging solutions to get the best fitness mutation
				double total = 0;
				for (int i = 0; i < solution.length; i++) {
					total += solution[i];
				}
				total/=20.0;
				for (int i = 0; i < solution.length; i++) {
					solution[i] = total;
				}
			}
		}

	}


	public static boolean[] solution2() {
		Candidate2 candidate = new Candidate2();
		candidate.fill();
		//The index 0 of tmp gives the weight. Index 1 gives the utility
        //System.out.println("The weight is: " + candidate.tempSol2[0]);
        //System.out.println("The utility is: " + candidate.tempSol2[1]);
		return candidate.bestSol;
	}


	static class Candidate2 {
		boolean[] sol2;
		boolean[] bestSol;

		//mutation methods to try get best possible utility / weight
		double original_mutate = 1;
		double mutate = original_mutate;
		double mutation_change = 0.999;
		double mutate_limit = 0.000001;
		double[] temp;
		
		public Candidate2() {
			//Solution 2 and best Solution to compare which one is best later on
			sol2 = new boolean[100];
			bestSol = new boolean[100];
			for (int i = 0; i < bestSol.length; i++) {
				//Randomly do false, true, false true to possible best solution
				bestSol[i] = (Math.random() < 0.5);
			}
		}

		public void fill() {
			while((System.currentTimeMillis() - startTime < 19500.0)) {
				//clone bestSol to solution 2
				sol2 = bestSol.clone();
				for (int i = 0; i < sol2.length; i++) {
					if(Math.random() < mutate) {
						//random less than mutation then sol2[i] is equal to not sol2[i]
						sol2[i] = (!sol2[i]);
					}
				}
				// System.out.print(checkFitness(sol2));
				// System.out.print(", ");
				// System.out.print(checkFitness(bestSol));
				// System.out.print(", ");
				// System.out.println(mutate);
				
				//use checkFitness function to see if sol2 fitness is better than bestSol
				if(checkFitness(sol2) > checkFitness(bestSol)) {
					//if better than clone sol2 into bestsol
					bestSol = sol2.clone();
				}
				//mutation methods
				if (mutate < mutate_limit)
					mutate = original_mutate;
				else
					mutate *= mutation_change;
			}
			
		}

		public double checkFitness(boolean[] sol) {
			//checking fitness of solutions
			temp = Assess.getTest2(sol);
			if(temp[0] > 500) {
				//if weight above 500 then return 500 - the weight
				return 500 - temp[0];
			}
			//return utility
			return temp[1];
		}

	}

}
