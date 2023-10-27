public class App {
    public static void main(String[] args) {
        Producer producer = new Producer();
        Thread prod = new Thread(producer);
        Thread cons = new Thread(new Consumer(producer));
        cons.start();
        prod.start();
    }

    public static class Producer implements Runnable {
        private int counter = 0;

        public int getCounter() {
            return counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                try {
                    counter++;
                    System.out.println(i);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Consumer implements Runnable {
        private Producer producer;

        public Consumer(Producer producer) {
            this.producer = producer;
        }

        @Override
        public void run() {
            while (producer.getCounter() <= 20) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("ВСЁ!!!!!!!!!!!!!!!!!!!!!![][][][][]");
        }
    }
}
