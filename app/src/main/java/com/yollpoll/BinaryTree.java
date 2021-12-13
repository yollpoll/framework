package com.yollpoll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Stream;

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
        printRes("Solution687", new Solution687().longestUnivaluePath(constructTree(1, 4, 5, 4, 4, 5)));


        Solution703.test();
        new Solution889().constructFromPrePost(new int[]{1, 2, 4, 5, 3, 6, 7}, new int[]{4, 5, 2, 6, 7, 3, 1});
        new Solution894().allPossibleFBT(7);
        boolean res = new Solution951().flipEquiv(constructTree(1, 2, 3, 4, 5, 6, null, null, null, 7, 8), constructTree(
                1, 3, 2, null, 6, 4, 5, null, null, null, null, 8, 7
        ));
        System.out.println(res);

        boolean res858
                = new Solution958().isCompleteTree(constructTree(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33));
        System.out.println("958res:" + res858);

        List<Integer> res971 = new Solution971().flipMatchVoyage(constructTree(1, 2, 3), 1, 3, 2);
        System.out.println("971:" + res971.size());
        new Solution988().smallestFromLeaf(constructTree(2, 2, 1, null, 1, 0, null, 0));
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
        serializeRes = "";
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
     * <p>
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
            int count = dfs(root);
            if (count > max) {
                max = count;
            }
            dfsAll(root.left);
            dfsAll(root.right);
        }

        //返回的是以root为根节点，自上到下的最长链
        public int dfs(TreeNode root) {
            if (null == root) {
                return 0;
            }
            int res1 = 0, res2 = 0;
            int left = dfs(root.left);
            int right = dfs(root.right);
            if (root.left != null && root.left.val == root.val) {
                res1 += left + 1;
            }
            if (root.right != null && root.right.val == root.val) {
                res2 += right + 1;
            }
            if (res1 + res2 > max) {
                max = res1 + res2;
            }
            return Math.max(res1, res2);
        }
    }


    /**
     * topK问题，建立大根堆
     * 从k+1开始遍历数组，进行比较操作，如果大于堆顶元素，则替换然后堆化一次
     */
    public static class Solution703 {
        public static int[] heap;

        public static void test() {
            int k = 3;
            int[] nums = {4, 5, 8, 2};
            heap = new int[k];
            for (int i = 0; i < k; i++) {
                heap[i] = nums[i];
            }
            heap = buildBigHeap(heap);
            for (int i = k; i < nums.length; i++) {
                if (nums[i] < heap[0]) {
                    heap[0] = nums[i];
                    adjustHeap(0, heap);
                }
            }
            printRes("heap", heap);
            printRes("heap", add(3));
            printRes("heap", add(5));
            printRes("heap", add(10));
            printRes("heap", add(9));
            printRes("heap", add(4));
        }

        private static int add(int k) {
            if (k < heap[0]) {
                heap[0] = k;
                adjustHeap(0, heap);
            }
            return heap[0];
        }

        /**
         * 建立堆
         *
         * @return
         */
        public static int[] buildBigHeap(int... num) {
            int lastIndex = (num.length / 2) - 1;
            for (int i = lastIndex; i >= 0; i--) {
                adjustHeap(i, num);
            }
            return num;
        }


        /**
         * 堆化操作
         *
         * @param i
         * @param num
         * @return
         */
        public static int[] adjustHeap(int i, int... num) {
            if (i >= num.length || i * 2 + 1 >= num.length) {
                return num;
            }
            if (i * 2 + 2 >= num.length) {
                if (num[i * 2 + 1] > num[i]) {
                    int temp = num[i];
                    num[i] = num[i * 2 + 1];
                    num[i * 2 + 1] = temp;
                    adjustHeap(i * 2 + 1, num);
                }
                return num;
            }
            if (num[i * 2 + 1] > num[i * 2 + 2]) {
                if (num[i * 2 + 1] > num[i]) {
                    int temp = num[i];
                    num[i] = num[i * 2 + 1];
                    num[i * 2 + 1] = temp;
                    adjustHeap(i * 2 + 1, num);
                }
            } else {
                if (num[i * 2 + 2] > num[i]) {
                    int temp = num[i];
                    num[i] = num[i * 2 + 2];
                    num[i * 2 + 2] = temp;
                    adjustHeap(i * 2 + 2, num);
                }
            }
            return num;
        }
    }

    /**
     * leetCode889
     * 根据前序遍历和后续遍历生成树
     * 通过找到preOrder中的midNode pre(1)在postOrder中的位置，找到左自树的长度
     */
    static class Solution889 {
        public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
            return createNode(preorder, 0, preorder.length - 1, postorder, 0, postorder.length - 1);
        }

        public TreeNode createNode(int[] preorder, int preL, int preR, int[] postorder, int postL, int postR) {
            if (preL > preR) {
                return null;
            }
            if (preL == preR) {
                return new TreeNode(preorder[preL]);
            }
            System.out.println(preL + "_" + preR);
            int leftVal = preorder[preL + 1];
            int leftTreeCount = findNumber(postorder, leftVal) - postL;
            TreeNode root = new TreeNode(preorder[preL]);
            TreeNode left = createNode(preorder, preL + 1, preL + leftTreeCount + 1, postorder, postL, postL + leftTreeCount);
            TreeNode right = createNode(preorder, preL + leftTreeCount + 2, preR, postorder, postL + leftTreeCount + 1, postR - 1);
            root.left = left;
            root.right = right;
            return root;

        }

        public int findNumber(int[] array, int target) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == target) {
                    return i;
                }
            }
            return -1;
        }
    }

    /**
     * leetCode894
     * 根据节点数生成所有可能的满二叉树
     * 满二叉树 ：节点只有0个子节点或者2个子节点
     */
    static class Solution894 {
        public List<TreeNode> allPossibleFBT(int n) {
            return dfs(n);
        }

        public List<TreeNode> dfs(int n) {
            List<TreeNode> res = new ArrayList<>();
            if (n == 1) {
                res.add(new TreeNode(0));
                return res;
            }
            for (int k = 1; k <= n - 2; k += 2) {
                List<TreeNode> leftList = dfs(k);
                List<TreeNode> rightList = dfs(n - 1 - k);
                for (TreeNode nodeLeft : leftList) {
                    for (TreeNode nodeRight : rightList) {
                        TreeNode root = new TreeNode(0);
                        root.left = nodeLeft;
                        root.right = nodeRight;
                        res.add(root);
                    }
                }
            }
            return res;
        }
    }

    /**
     * leetCode951
     * 判断翻转二叉树
     */
    static class Solution951 {
        public boolean flipEquiv(TreeNode root1, TreeNode root2) {
            if (root1 == null && root2 == null) {
                return true;
            }
            if (root1 != null && root2 == null) {
                return false;
            }
            if (root2 != null && root1 == null) {
                return false;
            }
            if (root1.val != root2.val) {
                return false;
            } else {
                return flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left);
            }
        }
    }

    static class Solution958 {
        public boolean isCompleteTree(TreeNode root) {
            //层数
            int deep = dfs(root) - 1;
            Deque<TreeNode> queue = new LinkedList<>();
            int level = 0;
            queue.offer(root);
            while (!queue.isEmpty()) {
                Deque<TreeNode> temp = new LinkedList<>();
                while (!queue.isEmpty()) {
                    temp.offer(queue.poll());
                }
                if (level <= deep - 1) {
                    //倒数第二层开始所有节点均有两个
                    if (temp.size() != Math.pow(2, level)) {
                        return false;
                    }
                }
                if (level == deep - 1) {
                    boolean end = false;
                    while (!temp.isEmpty()) {
                        TreeNode node = temp.poll();
                        if (end && (node.left != null || node.right != null)) {
                            return false;
                        }
                        if (node.left == null && node.right != null) {
                            return false;
                        } else if (node.left != null && node.right == null) {
                            //存在右节点是null，接下来但凡出现非空的子节点就fasle
                            end = true;
                        } else if (node.left == null) {
                            end = true;
                        }
                    }
                    return true;
                }
                while (!temp.isEmpty()) {
                    TreeNode node = temp.poll();
                    if (null != node.left) {
                        queue.offer(node.left);
                    }
                    if (null != node.right) {
                        queue.offer(node.right);
                    }
                }

                level++;
            }
            return true;

        }

        public int dfs(TreeNode root) {
            if (null == root) {
                return 0;
            }
            return Math.max(dfs((root.left)), dfs(root.right)) + 1;
        }

    }

    /**
     * leetCode971
     * 给出一个二叉树和一个先序遍历的数组，二叉树能否在翻转的子树的情况下实现遍历结果
     * 如果可以，返回翻转子树的节点值数组，如果不行，返回[-1]
     * <p>
     * <p>
     * 思路：如果root节点和nums[index]值相同，则比较left和nums[index++]
     * 如果left和nums[index++]不同，则翻转二叉树,进行下一轮遍历
     * 如果相同，则按照left->right的顺序遍历
     * index作全局变量，这样在第二个dfs的时候index会记录到相应位置
     * 遍历的先后决定了是否翻转二叉树
     * 注意：如果添加一个-1以后，接下来的解析都成功了，说明只在root节点出现了问题
     * 但是结果上还是需要返回-1，所以在所有遍历完成以后，检查最后的res数组，看是否存在-1，
     * （根据代码逻辑，如果存在-1，肯定是第一个，因为每次添加-1都会调用clear）
     */
    static class Solution971 {
        int[] voyage;
        int index = 0;
        List<Integer> res = new ArrayList<>();

        public List<Integer> flipMatchVoyage(TreeNode root, int... voyage) {
            this.voyage = voyage;
            dfs(root);
            //检查是否存在-1
            if (!res.isEmpty() && res.get(0) == -1) {
                res.clear();
                res.add(-1);
            }
            return res;
        }

        public void dfs(TreeNode root) {
            if (null == root) {
                return;
            }
            if (index >= voyage.length) {
                return;
            }
            if (root.val == voyage[index]) {
                index++;
            } else {
                res.clear();
                res.add(-1);
            }
            if (root.left != null && root.left.val != voyage[index]) {
                res.add(root.val);
                this.dfs(root.right);
                this.dfs(root.left);
            } else {
                this.dfs(root.left);
                this.dfs(root.right);
            }

        }
    }

    static class Solution988 {
        String ans="~";
        public String smallestFromLeaf(TreeNode root) {
            dfs(root,new StringBuilder());
            return ans;
        }

        public void dfs(TreeNode root, StringBuilder sb) {
            if (null == root) {
                return;
            }
            sb.append(((char) ('a' + root.val)));
            sb.reverse();
            String tempRes = sb.toString();
            if (tempRes.compareTo(ans) < 0) {
                ans = tempRes;
            }
            sb.reverse();
            dfs(root.left, sb);
            dfs(root.right, sb);
            sb.deleteCharAt(sb.length()-1);
        }


    }
}
