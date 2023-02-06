<template>
  <div>
    <el-card>
        <el-row>
            <el-col :span="3">
                <el-input placeholder="课程代码" v-model="courseCode"></el-input>
                <br />
                <br />
                <el-input placeholder="课程名称" v-model="courseName"></el-input>
            </el-col>
            <el-col :span="1"></el-col>
            <el-col :span="9">
                <el-table border height="200px" :data="course">
                    <el-table-column label="课程代码" prop="code" align="center">
                        <template v-slot="scope">
                            <div @click="addCourse(scope.row)">{{scope.row.code}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column label="课程名称" prop="name" align="center">
                        <template v-slot="scope">
                            <div @click="addCourse(scope.row)">{{scope.row.name}}</div>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
            <el-col :span="1"></el-col>
            <el-col :span="10">
                <el-table border height="200px" :data="courseData">
                    <el-table-column label="已选课程" prop="name" align="center">
                        <template v-slot="scope">
                            <div @click="removeCourse(scope.row)">{{scope.row.name}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column label="课程成绩" prop="score" align="center">
                        <template v-slot="scope">
                            <el-input v-model="scope.row.score" :placeholder="'请输入成绩,该课程为' + scope.row.credit + '学分'" :min="0" :max="100"></el-input>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <br />
        <el-row align="right">
            <el-col :span="16"></el-col>
            <el-col :span="1" align="center">
                <h3>GPA</h3>
            </el-col>
            <el-col :span="3" align="center">
                <h3>{{gpa}}/4.0</h3>
            </el-col>
            <el-col :span="2">
                <h3>智育分</h3>
            </el-col>
            <el-col :span="2">
                <h3>{{zyScore}}/70</h3>
            </el-col>
        </el-row>
        <br />
        <div class="top-line"></div>
        <br />
        <el-row>
            <el-col :span="9">
                <el-input placeholder="请填写第一学期体育成绩" v-model="term1" :min="0" :max="100"></el-input>
            </el-col>
            <el-col :span="1"></el-col>
            <el-col :span="9">
                <el-input placeholder="请填写第二学期体育成绩" v-model="term2" :min="0" :max="100"></el-input>
            </el-col>
            <el-col :span="1"></el-col>
            <el-col :span="2">
                <h3>体育分</h3>
            </el-col>
            <el-col :span="2">
                <h3>{{tyScore}}/10</h3>
            </el-col>
        </el-row>
        <br />
        <div class="top-line"></div>
        <br />
        <el-row>
            <el-col :span="3">
                <el-input placeholder="竞赛名称" v-model="contestName"></el-input>
                <br />
                <br />
                <el-select placeholder="青年大学习打卡次数" v-model="clock">
                    <el-option label="0" value="0"></el-option>
                    <el-option label="1" value="1"></el-option>
                    <el-option label="2" value="2"></el-option>
                    <el-option label="3" value="3"></el-option>
                    <el-option label="4" value="4"></el-option>
                    <el-option label="5" value="5"></el-option>
                    <el-option label="6" value="6"></el-option>
                    <el-option label="7" value="7"></el-option>
                    <el-option label="8" value="8"></el-option>
                    <el-option label="9" value="9"></el-option>
                    <el-option label="10" value="10"></el-option>
                    <el-option label="11" value="11"></el-option>
                    <el-option label="12" value="12"></el-option>
                    <el-option label="13" value="13"></el-option>
                    <el-option label="14" value="14"></el-option>
                    <el-option label="15" value="15"></el-option>
                    <el-option label="16" value="16"></el-option>
                </el-select>
            </el-col>
            <el-col :span="1"></el-col>
            <el-col :span="6">
                <el-table border height="200px" :data="contest">
                    <el-table-column label="竞赛名称" prop="name" align="center">
                        <template v-slot="scope">
                            <div @click="addContest(scope.row)">{{scope.row.name}}</div>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
            <el-col :span="1"></el-col>
            <el-col :span="13">
                <el-table border height="200px" :data="contestData">
                    <el-table-column label="获奖竞赛" prop="name" align="center">
                        <template v-slot="scope">
                            <div @click="removeContest(scope.row)">{{scope.row.name}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column label="竞赛等级" prop="score" align="center">
                        <template v-slot="scope">
                            <el-select placeholder="请选择竞赛等级" v-model="scope.row.level">
                                <el-option label="不评选等级" value="不评选等级"></el-option>
                                <el-option label="校级" value="校级"></el-option>
                                <el-option label="市级" value="市级"></el-option>
                                <el-option label="国家级" value="国家级"></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column label="获奖等级" prop="score" align="center">
                        <template v-slot="scope">
                            <el-select placeholder="请选择获奖等级" v-model="scope.row.lv">
                                <el-option label="不评选等级" value="不评选等级"></el-option>
                                <el-option label="一等奖" value="一等奖"></el-option>
                                <el-option label="二等奖" value="二等奖"></el-option>
                                <el-option label="三等奖" value="三等奖"></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <br />
        <el-row align="right">
            <el-col :span="20"></el-col>
            <el-col :span="2">
                <h3>加分项</h3>
            </el-col>
            <el-col :span="2">
                <h3>{{extroScore}}/8</h3>
            </el-col>
        </el-row>
        <br />
        <div class="top-line"></div>
        <br />
        <el-row>
            <el-col :span="18">
                <h4>*注：本学年互评分采取14.9-15中的随机数，为[{{randScore}}分]</h4>
            </el-col>
            <el-col :span="2">
                <el-button type="primary" @click="$router.go(0)">刷新</el-button>
            </el-col>
            <el-col :span="4">
                <el-button type="primary" @click="resDialog = !resDialog">计算综测得分</el-button>
            </el-col>
        </el-row>
        <el-dialog v-model="resDialog" >
            <el-row>
                <el-col :span="11">
                    <img align="right" src="../assets/images/wyz.jpg" height="120">
                </el-col>
                <el-col :span="2"></el-col>
                <el-col :span="11">
                    <p>您的综合测评成绩为</p>
                    <h1>{{(Number(zyScore) + Number(tyScore) + Number(extroScore) + Number(randScore)).toFixed(2)}}</h1>
                </el-col>
            </el-row>
        </el-dialog>
    </el-card>
  </div>
</template>

<script lang="ts">
import { getCurrentInstance, ref, computed, watch } from "vue";
import { HttpManager } from "@/api";

export default {
    setup() {
        const { proxy } = getCurrentInstance();
        const resDialog = ref(false);
        const allCourse = ref([]);
        const allContest = ref([]);
        const course = computed(() => {
            return allCourse.value.filter((item) => {
                if (typeof courseName.value == "undefined" && typeof courseCode.value == "undefined") return true;
                else if (typeof courseName.value == "undefined") return item.code.indexOf(courseCode.value) == 0;
                else if (typeof courseCode.value == "undefined") return item.name.indexOf(courseName.value) != -1;
                else return item.code.indexOf(courseCode.value) == 0 && item.name.indexOf(courseName.value) != -1;
            });
        });
        const contest = computed(() => {
            if (typeof contestName.value == "undefined") return allContest.value;
            else return allContest.value.filter((item) => (item.name.indexOf(contestName.value) != -1));
        });
        const courseData = ref([]);
        const contestData = ref([]);
        const courseCode = ref();
        const courseName = ref();
        const contestName = ref();
        const term1 = ref();
        const term2 = ref();
        const clock = ref();
        const gpa = computed(() => {
            let gp = 0, credits = 0;
            courseData.value.forEach((item) => {
                if (typeof item.score == "undefined" || item.score == null || item.score == "") return;
                else if (item.score < 60 && item.score >= 0) gp += 0.0;
                else if (item.score < 64) gp += 1.0 * item.credit;
                else if (item.score < 68) gp += 1.7 * item.credit;
                else if (item.score < 72) gp += 2.0 * item.credit;
                else if (item.score < 75) gp += 2.3 * item.credit;
                else if (item.score < 78) gp += 2.7 * item.credit;
                else if (item.score < 82) gp += 3.0 * item.credit;
                else if (item.score < 85) gp += 3.3 * item.credit;
                else if (item.score < 90) gp += 3.7 * item.credit;
                else if (item.score <= 100) gp += 4.0 * item.credit;
                else (proxy as any).$message({
                    message:"我想您的成绩应当为与0-100的区间",
                    type:"error"
                });
                credits += item.credit;
            });
            if (credits == 0) return 0;
            else return (gp / credits).toFixed(2);
        });
        const zyScore = computed(() => {
            let score = 0, credits = 0;
            courseData.value.forEach((item) => {
                if (typeof item.score == "undefined" || item.score == null || item.score == "") return;
                score += item.credit * item.score;
                credits += item.credit;
            });
            if (credits == 0) return 0;
            else return (score / credits * 0.6).toFixed(2);
        });
        const tyScore = computed(() =>{
            if (typeof term2.value == "undefined" || term2.value == null || term2.value == "") return 0.00;
            return ((Number(term1.value) + Number(term2.value)) / 20).toFixed(2);
        });
        const extroScore = computed(() => {
            let extro = 0;
            contestData.value.forEach((item) => {
                if (typeof item.level == "undefined" || item.level == null || item.level == "") return;
                else if (typeof item.lv == "undefined" || item.lv == null || item.lv == "") return;
                else if (item.level == "校级") extro += 0.5;
                else if (item.level == "市级") extro += 1.6;
                else if (item.level == "国家级") extro += 2;
                else extro += Number(item.credit);
            });
            if (typeof clock.value == "undefined" || clock.value == null || clock.value == "") return (2.5 + (extro < 3.5 ? extro : 3.5)).toFixed(2);
            return (2.5 - 0.1 * (16 - Number(clock.value)) + (extro < 3.5 ? extro : 3.5)).toFixed(2);
        });
        const randScore = (14.9 + (Math.random() / 10)).toFixed(2);
        getData();

        async function getData() {
            allCourse.value = [];
            allContest.value = [];
            const courseResult = (await HttpManager.getAllCourse()) as ResponseBody;
            allCourse.value = courseResult.data;
            const contestResult = (await HttpManager.getAllContest()) as ResponseBody;
            allContest.value = contestResult.data;
        }

        function addCourse(row) {
            if (!courseData.value.includes(row)) {
                (proxy as any).$message({message: "选课成功", type: "success"});
                courseData.value.push(row);
            } else (proxy as any).$message({message: "选课失败，您已选择该课程", type: "error"});
        }

        function addContest(row) {
            if (!contestData.value.includes(row)) {
                (proxy as any).$message({message: "参赛成功，且直接获奖", type: "success"});
                contestData.value.push(row);
            } else (proxy as any).$message({message: "参赛失败，您已参加该竞赛", type: "error"});
        }

        function removeCourse(row) {
            courseData.value = courseData.value.filter((item) => (item.id != row.id));
            (proxy as any).$message({message: "退课成功", type: "success"});
        }

        function removeContest(row) {
            contestData.value = contestData.value.filter((item) => (item.id != row.id));
            (proxy as any).$message({message: "退赛成功", type: "success"});
        }

        return {
            allCourse,
            allContest,
            courseCode,
            courseName,
            contestName,
            course,
            contest,
            resDialog,
            courseData,
            contestData,
            term1,
            term2,
            clock,
            gpa,
            zyScore,
            tyScore,
            extroScore,
            randScore,
            addCourse,
            addContest,
            removeCourse,
            removeContest
        }
    }
}
</script>

<style>
    .top-line {
        border-top: 1px solid rgb(197, 197, 196);
    }
</style>