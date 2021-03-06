package grep;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

/** Search web pages for lines matching a pattern. */
public class Grep {
    public static void main(String[] args) throws Exception {

        // pattern to search for
        Pattern pattern = Pattern.compile("\\p{Upper}\\w*\\.\\p{Lower}\\w*\\(");

        // URLs to search
        String[] urls = new String[] {
                "http://web.mit.edu/6.005/www/sp13/psets/ps0/",
                "http://web.mit.edu/6.005/www/sp13/psets/ps1/",
                "http://web.mit.edu/6.005/www/sp13/psets/ps2/",
                "http://web.mit.edu/6.005/www/sp13/psets/ps3/", };

        // list for accumulating matching lines
        List<Line> matches = Collections
                .synchronizedList(new ArrayList<Line>());

        // queue for sending lines from producers to consumers
        BlockingQueue<Line> queue = new LinkedBlockingQueue<Line>();

        Thread[] producers = new Thread[urls.length]; // one producer per URL
        Thread[] consumers = new Thread[urls.length]; // TODO use multiple consumers

        for (int ii = 0; ii < consumers.length; ii++) { // start Consumers
            Thread consumer = consumers[ii] = new Thread(new Consumer(pattern,
                    queue, matches));
            consumer.start();
        }

        for (int ii = 0; ii < urls.length; ii++) { // start Producers
            Thread producer = producers[ii] = new Thread(
                    new Producer(urls[ii], queue));
            producer.start();
        }

        for (Thread producer : producers) { // wait for Producers to stop
            producer.join();
        }

        for (Thread consumer : consumers) { // wait for Consumers to stop
            consumer.join();
        }

        for (Line match : matches) {
            System.out.println(match);
        }
    }
}

class Producer implements Runnable {

    String url;
    BlockingQueue<Line> queue;

    Producer(String url, BlockingQueue<Line> queue) {
        this.url = url;
        this.queue = queue;
    }

    public void run() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    new URL(url).openStream()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line;
        int i = 0;

        try {
            while ((line = in.readLine()) != null) {
                Line input = new Text(this.url, i, line);
                queue.put(input);
            }
            queue.put(new Text("poison", -1, "poison"));
            
            // TODO read lines and push them onto the queue for consumers
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class Consumer implements Runnable {
    private BlockingQueue<Line> queue;
    private List<Line> matches;
    private Pattern pattern;
    
    Consumer(Pattern pattern, BlockingQueue<Line> queue, List<Line> matches) {
        this.queue = queue;
        this.matches = matches;
        this.pattern = pattern;
    }

    public void run() {
        while (true) {
            try {
                Line line = this.queue.take();
                
                if (line.text().equals("poison")) {
                    break;
                }
                if (found(this.pattern, line.text())) {
                    this.matches.add(line);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }           
        }
    }
    
    
    /** Return true iff the given pattern appears in the given text. */
    private boolean found(Pattern p, CharSequence cs) {
        return p.matcher(cs).find();
    }

}

interface Line {
    /** Return the filename. */
    public String filename();

    /** Return the line number. */
    public int lineNo();

    /** Return the text on the line. */
    public String text();
}

class Text implements Line {
    private final String filename;
    private final int lineNo;
    private final String text;

    public Text(String filename, int lineNo, String text) {
        this.filename = filename;
        this.lineNo = lineNo;
        this.text = text;
    }

    public String filename() {
        return filename;
    }

    public int lineNo() {
        return lineNo;
    }

    public String text() {
        return text;
    }

    @Override
    public String toString() {
        return filename + ":" + lineNo + ":" + text;
    }
}
