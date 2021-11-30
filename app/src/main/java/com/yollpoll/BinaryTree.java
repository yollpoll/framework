package com.yollpoll;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by spq on 2021/10/26
 * 二叉树
 */
public class BinaryTree {
    public static void main(String[] args) {
        Integer[] array = new Integer[]{3, 1, 4, null, null, 2};
        TreeNode root = constructTree(array);
        swipeBST(root);
        //序列化反序列化
        Integer[] array2 = new Integer[]{2, 1, 3};
        TreeNode root2 = constructTree(array2);
        deserialize(serialize(root2));

        printRes("501", new Solution501().findMode(constructTree(1, null, 2, 2)));
        printRes("Solution513", new Solution513().findBottomLeftValue(constructTree(2, 1, 3)));

        List<Integer> res513 = new Solution515().largestValues(constructTree(1, 3, 2, 5, 3, null, 9));
        int[] temp = new int[res513.size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = res513.get(i);
        }
        printRes("Solution515", temp);

        printRes("Solution543", new Solution543().diameterOfBinaryTree(constructTree(1, 2, 3, 4, 5)));

        printRes("Solution662", new Solution662_2().widthOfBinaryTree(constructTree(1, 1, 1, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, 1, null, null, 1, 1, null, 1, null, 1, null, 1, null, 1, null)));
        printRes("Solution687", new Solution687().longestUnivaluePath(constructTree(1,4,5,4,4,5)));
    }


    /**
     * leetCode 99题
     * BST中有两个节点错误的交换了，需要交换回来
     */
    public static void swipeBST(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        list = generateList(root, list);
        findError(list);
    }

    //拿到中序遍历列表
    public static List<TreeNode> generateList(TreeNode root, List<TreeNode> list) {
        Stack<TreeNode> stack = new Stack();
        while (null != root || !stack.isEmpty()) {
            if (null != root) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                list.add(root);
                System.out.println("value is " + root.val);
                root = root.right;
            }
        }
        return list;
    }

    public static List<TreeNode> findError(List<TreeNode> list) {
        if (list.size() <= 1) {
            return list;
        }
        int x = -1, y = -1;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).val >= list.get(i + 1).val) {
                //找到了不是升序的节点i
                //两个节点错误交换，所以存在两个点i，j有 list(i)>list(i+1),list(j)>list(j+1)
                //所以第一个错误节点为i，第二个错误节点为j+1
                //x=i+1这一步，如果交换的点相邻，则只会走一次，如果不相邻，则会走2次，x取到的是第二次的节点位置j
                x = i + 1;
                //y根据是否已经赋值，如果没有赋值，则把当前错误的点i赋值给y
                //如果已经赋值了，则退出整个循环
                if (y == -1) {
                    y = i;
                } else {
                    //当前已经赋值，两个节点都找到了，退出循环
                    break;
                }
            }
        }
        //交换位置
        int temp = list.get(x).val;
        list.get(x).val = list.get(y).val;
        list.get(y).val = temp;
        return list;
    }

    ////////////////////////////////////////////序列化反序列化 start//////////////////////////////////////
    // Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        dfs(root);
        System.out.println("serialize:" + serializeRes);
        return serializeRes;
    }

    public static String serializeRes = "";//这里需要初始化，不然会存在null的字符

    public static void dfs(TreeNode node) {
        if (null == node) {
            serializeRes += "#,";
            return;
        }
        serializeRes += node.val + ",";
        dfs(node.left);
        dfs(node.right);

    }

    public static Deque<String> queue = new LinkedList<>();//使用队列，这样拿取数据使用和序列化一样的顺序

    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        String[] list = data.split(",");
        for (String s : list) {
            queue.offer(s);
        }
        TreeNode root = createNode();
        return root;
    }

    public static TreeNode createNode() {
        String val = queue.pop();
        if ("#".equals(val)) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left = createNode();
        node.right = createNode();
        return node;
    }
/////////////////////////////////////////序列化反序列化 end/////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 通过数组构建二叉树
     *
     * @param array
     * @return
     */
    public static TreeNode constructTree(Integer... array) {
        if (array == null || array.length == 0 || array[0] == null) {
            return null;
        }

        int index = 0;
        int length = array.length;

        TreeNode root = new TreeNode(array[0]);
        Deque<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.offer(root);
        TreeNode currNode;
        while (index < length) {
            index++;
            if (index >= length) {
                return root;
            }
            currNode = nodeQueue.poll();
            Integer leftChild = array[index];
            if (leftChild != null) {
                currNode.left = new TreeNode(leftChild);
                nodeQueue.offer(currNode.left);
            }
            index++;
            if (index >= length) {
                return root;
            }
            Integer rightChild = array[index];
            if (rightChild != null) {
                currNode.right = new TreeNode(rightChild);
                nodeQueue.offer(currNode.right);
            }
        }

        return root;
    }

    public static class TreeNode {
        public int val;
        public TreeNode left, right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * 打印结果
     *
     * @param value
     */
    static void printRes(String title, int... value) {
        if (null == value || value.length == 0) {
            System.out.println("[]");
        }
        StringBuilder res = new StringBuilder("[");
        for (int i = 0; i < value.length; i++) {
            if (i != value.length - 1) {
                res.append(value[i] + ",");
            } else {
                res.append(value[i]);
            }
        }
        System.out.println(title + ": " + res + "]");
    }

    /**
     * leetCode 501
     * 找到bst众数
     */
    static class Solution501 {
        Map<Integer, Integer> map = new HashMap<>();

        public int[] findMode(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            dfs(root);
            int maxCount = Integer.MIN_VALUE;

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getValue() >= maxCount) {
                    maxCount = entry.getValue();
                }
            }

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getValue() == maxCount) {
                    res.add(entry.getKey());
                }
            }
            int[] result = new int[res.size()];
            for (int i = 0; i < res.size(); i++) {
                result[i] = res.get(i);
            }
            return result;
        }

        public void dfs(TreeNode root) {
            if (root == null) {
                return;
            }
            if (map.containsKey(root.val)) {
                int count = map.get(root.val);
                map.put(root.val, ++count);
            } else {
                map.put(root.val, 1);
            }
            dfs(root.left);
            dfs(root.right);
        }
    }

    /**
     * leetCode找到树最下层最左边的值
     */
    static class Solution513 {
        public int findBottomLeftValue(TreeNode root) {
            bfs(root);
            return res.val;
        }

        Deque<TreeNode> queue = new LinkedList<>();
        TreeNode res;

        public void bfs(TreeNode root) {
            queue.offer(root);
            while (!queue.isEmpty()) {
                List<TreeNode> level = new ArrayList<>();
                while (!queue.isEmpty()) {
                    level.add(queue.poll());
                }
                res = level.get(0);
                for (TreeNode node : level) {
                    if (null != node.left) {
                        queue.offer(node.left);
                    }
                    if (null != node.right) {
                        queue.offer(node.right);
                    }
                }
            }
        }
    }

    /**
     * 找到一层中最大的值
     */
    static class Solution515 {
        public List<Integer> largestValues(TreeNode root) {
            bfs(root);
            return result;
        }

        Deque<TreeNode> queue = new LinkedList<>();
        List<Integer> result = new ArrayList<>();

        public void bfs(TreeNode root) {
            if (null == root) {
                return;
            }
            queue.offer(root);
            while (!queue.isEmpty()) {
                List<TreeNode> level = new ArrayList<>();
                while (!queue.isEmpty()) {
                    level.add(queue.poll());
                }
                int maxValue = Integer.MIN_VALUE;
                TreeNode maxNode = null;
                for (TreeNode node : level) {
                    if (node.val > maxValue) {
                        maxNode = node;
                        maxValue = node.val;
                    }
                }
                if (null != result) {
                    result.add(maxNode.val);
                }
                for (TreeNode node : level) {
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }

            }
        }
    }

    /**
     * leetCode543找到二叉树最大半径
     * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
     */
    static class Solution543 {
        public int diameterOfBinaryTree(TreeNode root) {
            dfsAll(root);
            return maxDeep;
        }

        public void dfsAll(TreeNode root) {
            if (null == root) {
                return;
            }
            dfs(root);
            dfs(root.left);
            dfs(root.right);
        }

        int maxDeep = 0;

        public int dfs(TreeNode root) {
            if (null == root) {
                return 0;
            }
            int leftDeep = dfs(root.left);
            int rightDeep = dfs(root.right);
            if (leftDeep + rightDeep > maxDeep) {
                maxDeep = leftDeep + rightDeep;
            }
            return Math.max(leftDeep + 1, rightDeep + 1);
        }
    }


    /**
     * leetCode 662
     * 给定一个二叉树，编写一个函数来获取这个树的最大宽度。树的宽度是所有层中的最大宽度。这个二叉树与满二叉树（full binary tree）结构相同，但一些节点为空。
     * <p>
     * 每一层的宽度被定义为两个端点（该层最左和最右的非空节点，两端点间的null节点也计入长度）之间的长度。
     * 这种办法会有问题，如果中间存在大量null节点，
     * 直接保存节点会随着层数大量上升，会出现oom，应该不缓存null节点，而是将节点的位置position记录下来
     * 最左边和最右边的节点的positionRight-positionLeft+1即为宽度，详情看662_2
     */
    static class Solution662 {
        int max = Integer.MIN_VALUE;

        public int widthOfBinaryTree(TreeNode root) {
            if (null == root) {
                return 0;
            }
            Deque<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                //每一层的节点(包含null节点)
                Deque<TreeNode> level = new LinkedList<>();
                while (!queue.isEmpty()) {
                    level.offer(queue.poll());
                }
                //裁剪掉最右边的null节点
                while (!level.isEmpty() && level.peekLast() == null) {
                    level.pollLast();
                }
                //裁剪掉最左边的null节点
                while (!level.isEmpty() && level.peekFirst() == null) {
                    level.pollFirst();
                }
                //当前level的宽度
                if (level.size() > max) {
                    max = level.size();
                }
                while (!level.isEmpty()) {
                    TreeNode node = level.poll();
                    if (node == null) {
                        queue.offer(null);
                        queue.offer(null);
                    } else {
                        queue.offer(node.left);
                        queue.offer(node.right);
                    }
                }
            }
            return max;
        }
    }

    /**
     * leetCode 662
     * 可以新建一种数据结构，同时记录节点的position
     */
    static class Solution662_2 {
        int max = Integer.MIN_VALUE;

        public int widthOfBinaryTree(TreeNode root) {
            if (null == root) {
                return 0;
            }
            Deque<PositionNode> queue = new LinkedList<>();
            queue.offer(new PositionNode(0, root));
            while (!queue.isEmpty()) {
                Deque<PositionNode> level = new LinkedList<>();
                while (!queue.isEmpty()) {
                    level.offer(queue.poll());
                }
                if (level.isEmpty()) {
                    break;
                }
                int width = level.peekLast().position - level.peekFirst().position;
                //当前level的宽度
                if (width > max) {
                    max = width;
                }
                while (!level.isEmpty()) {
                    PositionNode node = level.poll();
                    if (null != node.node.left) {
                        PositionNode leftNode = new PositionNode(node.position * 2, node.node.left);
                        queue.offer(leftNode);
                    }
                    if (null != node.node.right) {
                        PositionNode right = new PositionNode((1 + node.position * 2), node.node.right);
                        queue.offer(right);
                    }
                }
            }
            return max;
        }

        //辅助数据对象
        public static class PositionNode {
            public PositionNode(int position, TreeNode node) {
                this.position = position;
                this.node = node;
            }

            public PositionNode() {
            }

            public int position;
            public TreeNode node;
        }
    }

    /**
     * leetCode687
     * 给定一个二叉树，找到最长的路径，这个路径中的每个节点具有相同值。 这条路径可以经过也可以不经过根节点。
     * <p>
     * 注意：两个节点之间的路径长度由它们之间的边数表示。
     *
     * 求任意一条路径的问题，往往可以转化成遍历每个节点，以当前节点为顶点的路径问题，故需要2重迭代
     * 内部迭代含义为以当前root节点为顶点的树为路径必经点
     * 有3种情况，left-root-parent为最大路径
     * right-root-parent为最大路径
     * left-root-right为最大路径
     * 前两种情况直接返回节点数量
     * 最后一种情况下和最大值比较一下
     */
    static class Solution687 {
        int max = Integer.MIN_VALUE;

        public int longestUnivaluePath(TreeNode root) {
            if (null == root) {
                return 0;
            }
            dfs(root);
            return max;
        }

        public void dfsAll(TreeNode root) {
            if (root == null) {
                return;
            }
            int count=dfs(root);
            if(count>max){
                max=count;
            }
            dfsAll(root.left);
            dfsAll(root.right);
        }

        //返回的是以root为根节点，自上到下的最长链
        public int dfs(TreeNode root){
            if (null == root) {
                return 0;
            }
            int res1=0,res2=0;
            int left=dfs(root.left);
            int right=dfs(root.right);
            if (root.left != null&&root.left.val == root.val) {
                res1+=left+1;
            }
            if (root.right != null&&root.right.val == root.val) {
                res2+=right+1;
            }
            if(res1+res2>max){
                max=res1+res2;
            }
            return Math.max(res1, res2);
        }
    }

}
