package org.java.demo;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivitiApi {

    private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义
     */
    @Test
    public void deploymentDefinition(){

        RepositoryService repositoryService = engine.getRepositoryService();

        //读取文件
        InputStream bpmn_in = this.getClass().getClassLoader().getResourceAsStream("diagrams/purchar.bpmn");
        InputStream png_in  = this.getClass().getClassLoader().getResourceAsStream("diagrams/purchar.png");

        //指定文件名
        String bpmn = "purchar.bpmn";
        String png ="purchar.png";

        Deployment deploy = repositoryService.createDeployment().addInputStream(bpmn, bpmn_in).addInputStream(png, png_in).deploy();


        System.out.println("部署成功，部署id是:"+deploy.getId());

    }



    @Test
    public void startInstance(){
        RuntimeService runtimeService = engine.getRuntimeService();
        String processDefinitionKey = "myProcess_1";

        Map<String,Object> map = new HashMap<>();
        map.put("price",900 );

        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefinitionKey, map);

        System.out.println("实例已启动，流程实例的id是 ："+instance.getProcessInstanceId());
    }





    @Test
    public void queryPersonTask(){

        TaskService taskService = engine.getTaskService();

        //创建任务查询对象
        TaskQuery query = taskService.createTaskQuery();

        //指定要查询哪一个用户的任务
        String assignee="xufen";

        //指定任务的负责人
        query.taskAssignee(assignee);

        //查询所有任务
        List<Task> list = query.list();

        for(Task t:list){
            System.out.println("任务id："+t.getId());
            System.out.println("任务名称:"+t.getName());
            System.out.println("任务办理人:"+t.getAssignee());
            System.out.println("任务开始时间:"+t.getCreateTime());
            System.out.println("------------------------------------------");
        }

    }


    /**
     * 完成任务:
     *
     * 涉及到服务:TaskService
     * 涉及到的表: act_ru_task
     */
    @Test
    public void completeTask(){
        TaskService taskService = engine.getTaskService();
        String taskId="105";
        taskService.complete(taskId);
        System.out.println("任务已完成");
    }





}
