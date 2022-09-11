package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer fileReadTemplate(String path, BufferedReaderCallback callback) throws IOException {
        BufferedReader bufferedReader = null;

        try {
            // 한 줄씩 읽기 편하게 BufferedReader 로 파일을 가져온다.
            bufferedReader = new BufferedReader(new FileReader(path));
            Integer result = callback.doSomethingWithReader(bufferedReader);
            return result;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initValue) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filepath));
            T result = initValue;
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                result = callback.doSomethingWithLine(line, result);
            }
            return result;
        }  catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public int calcSum(String path) throws IOException {
        LineCallback<Integer> sumCallback = (line, value) -> value + Integer.parseInt(line);
        return lineReadTemplate(path, sumCallback, 0);
    }

    public int calcMultiply(String path) throws IOException {
        LineCallback<Integer> multiplyCallback = (line, value) -> value * Integer.parseInt(line);
        return lineReadTemplate(path, multiplyCallback, 1);
    }

    public String concatenate(String path) throws IOException {
        LineCallback<String> concatenateCallback = (line, value) -> value + line;
        return lineReadTemplate(path, concatenateCallback, "");
    }
}
