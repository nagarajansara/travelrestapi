package Thread;


//How to make a while to run until scanner get input?

import java.util.Scanner;

public class Sample
{
	public static void main(String args[])
	{
		SampleThread test = new SampleThread();
		Thread t = new Thread(test);
		t.start();

		Scanner s = new Scanner(System.in);
		while (!s.next().equals("stop"))
		test.keepRunning = false;
		t.interrupt(); // cancel current sleep.
	}

}

class SampleThread implements Runnable
{
	boolean keepRunning = true;

	public void run()
	{
		while (keepRunning)
		{
			System.out.println("Running loop...");
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
			}
		}
		System.out.println("Done looping.");
	}
}
