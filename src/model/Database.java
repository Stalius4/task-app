package model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.File;
import java.util.Date;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Database {
    // The volatile keyword ensures the instance variable is correctly published across threads
    private static final String FILE_PATH = "./tasks.xml";
    private static volatile Database instance;
    private ArrayList<Task> taskList;
    private int id;
    // Private constructor prevents instantiation from other classes


    private Database() {
        taskList = new ArrayList<>();

        // Prevent reflection instantiation
        if (instance != null) {
            throw new IllegalStateException("Already initialized.");
        }
    }

    /**
     * Returns the singleton Database instance, creating it on first access.
     *
     * This method uses lazy initialization with double-checked locking to ensure
     * the Database is created only once in a thread-safe manner.
     *
     * @return the shared Database singleton
     */
    public static Database getInstance() {
        // First check (no synchronization)
        if (instance == null) {
            // Synchronize only if instance is null
            synchronized (Database.class) {
                // Second check (with synchronization)
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }


    /**
     * Marks the task with the given id as completed in the persistent XML store.
     *
     * Loads the XML file at FILE_PATH, finds the <task> element whose "id" attribute
     * equals the provided id, sets its "completed" attribute to "true", and writes
     * the updated document back to the file. The method prints the matched task's
     * title and prints each task id encountered while scanning.
     *
     * @param id the task id to mark completed (compared against each task element's "id" attribute)
     * @throws RuntimeException if parsing, reading, or writing the XML fails
     */
    public void changeStatus( String id){
        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(FILE_PATH);
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            // get all task elements
            NodeList taskNodes = root.getElementsByTagName("task");

            for (int i = 0; i < taskNodes.getLength(); i++) {
                Node taskNode = taskNodes.item(i);

                if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element taskElement = (Element) taskNode;

                String idStr  = taskElement.getAttribute("id");
                if(idStr.equals(id)){
                    String taskTitle = taskElement.getElementsByTagName("title").item(0).getTextContent();
                    System.out.print(taskTitle);
                    taskElement.setAttribute("completed","true");
                }
                System.out.println(idStr);


            }
                saveXml(doc);
            }




        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Adds a Task to the in-memory list and persists it as a new `<task>` element in the XML file at {@code FILE_PATH}.
     *
     * The persisted XML task will have attributes `completed="false"` and `id` set from {@code task.getId()},
     * and child elements `title`, `description`, and `CreatedAt`. The `CreatedAt` timestamp is formatted using
     * pattern "MM-dd-yyyy hh:mm:ss a".
     *
     * @param task the Task to add; its id, title, description, and date are used when creating the XML entry
     */
    public void addTask(Task task) {
        taskList.add(task);

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(FILE_PATH);
            doc.getDocumentElement().normalize();

            // Create new task element
            Element newTask = doc.createElement("task");
            newTask.setAttribute("completed", String.valueOf(false));
            newTask.setAttribute("id", String.valueOf(task.getId()));

            // Create title element
            Element taskTitle = doc.createElement("title");
            taskTitle.appendChild(doc.createTextNode(task.getTitle()));
            newTask.appendChild(taskTitle);

            // Create description element
            Element taskDescription = doc.createElement("description");
            taskDescription.appendChild(doc.createTextNode(String.valueOf(task.getDescription())));
            newTask.appendChild(taskDescription);

            //Create time stamp
            Element taskTime = doc.createElement("CreatedAt");
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
            taskTime.appendChild(doc.createTextNode(formatter.format(task.getDate())));

            newTask.appendChild(taskTime);



            // Append new task to the root
            doc.getDocumentElement().appendChild(newTask);
            saveXml(doc);
            System.out.println("Student created: " + task.getTitle());

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     * Loads tasks from the XML file (FILE_PATH) and populates the in-memory taskList.
     *
     * Parses each <task> element under the document root and constructs a corresponding
     * Task object using the element's title, description, CreatedAt (parsed with pattern
     * "MM-dd-yyyy hh:mm:ss a"), completed attribute (as boolean), and id attribute (as UUID).
     * If the CreatedAt value cannot be parsed, the current date is used as a fallback.
     *
     * Side effects:
     * - Clears or appends to the in-memory taskList (as implemented) by adding each loaded Task.
     * - Reads from and depends on the FILE_PATH XML file structure (expects <task> children
     *   with <title>, <description>, <CreatedAt> and attributes "id" and "completed").
     */
    public void getData() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            File file = new File(FILE_PATH);
            Document doc = builder.parse(file);

            // get the root element
            Element root = doc.getDocumentElement();
        
            // get all task elements
            NodeList taskNodes = root.getElementsByTagName("task");
        
            // iterate through each task
            for (int i = 0; i < taskNodes.getLength(); i++) {
                Node taskNode = taskNodes.item(i);
            
                if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element taskElement = (Element) taskNode;
                
                    // get title and description
                    String title = taskElement.getElementsByTagName("title").item(0).getTextContent();
                    String description = taskElement.getElementsByTagName("description").item(0).getTextContent();
                    String dateStr = taskElement.getElementsByTagName("CreatedAt").item(0).getTextContent();
                    String idStr  = taskElement.getAttribute("id");
                    boolean status = Boolean.parseBoolean(taskElement.getAttribute("completed"));
                    Date date = null;
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
                        date = formatter.parse(dateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        date = new Date(); // fallback to current date
                    }


                    UUID taskId = idStr.isEmpty() ? null : UUID.fromString(idStr);
                    Task task = new Task(title, description, date, status, taskId);
                    taskList.add(task);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private static void saveXml(Document doc) {
        try {
            // Remove empty text nodes and normalize whitespace before saving
            removeEmptyTextNodes(doc.getDocumentElement());
        
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            // Set proper indentation properties
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
        
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_PATH));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to remove empty text nodes
    private static void removeEmptyTextNodes(Node node) {
        NodeList children = node.getChildNodes();
        for (int i = children.getLength() - 1; i >= 0; i--) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE) {
                String textContent = child.getTextContent().trim();
                if (textContent.isEmpty()) {
                    node.removeChild(child);
                }
            } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                removeEmptyTextNodes(child);
            }
        }
    }
    public ArrayList<Task> getTaskList(){
        return taskList;
    }
}