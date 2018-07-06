package com.wz.guava.utilities;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Calendar;

/**
 * @projectName: Guava
 * @package: com.wz.guava.utilities
 * @className: ObjectsTest
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/7/6 20:41
 **/
public class ObjectsTest {

    static class Guava {
        private String manufacture;

        private String version;

        private Calendar releaseDate;

        public Guava(String manufacture, String version, Calendar releaseDate) {
            this.manufacture = manufacture;
            this.version = version;
            this.releaseDate = releaseDate;
        }

        /**
         * 使用idea生成代码快捷键,使用guava的方式生成代码
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Guava guava = (Guava) o;
            return Objects.equal(manufacture, guava.manufacture) &&
                    Objects.equal(version, guava.version) &&
                    Objects.equal(releaseDate, guava.releaseDate);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(manufacture, version, releaseDate);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("manufacture", manufacture)
                    .add("version", version)
                    .add("releaseDate", releaseDate)
                    .toString();
        }

        /**
         * 使用idea默认生成的代码
         */
        /*@Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Guava guava = (Guava) o;

            if (!manufacture.equals(guava.manufacture)) return false;
            if (!version.equals(guava.version)) return false;
            return releaseDate.equals(guava.releaseDate);
        }

        @Override
        public int hashCode() {
            int result = manufacture.hashCode();
            result = 31 * result + version.hashCode();
            result = 31 * result + releaseDate.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Guava{" +
                    "manufacture='" + manufacture + '\'' +
                    ", version='" + version + '\'' +
                    ", releaseDate=" + releaseDate +
                    '}';
        }*/
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        Guava guava = new Guava("Google", "23.0", calendar);
        System.out.println(guava);
        System.out.println(guava.hashCode());

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.YEAR, 0);
        Guava guava2 = new Guava("Google", "23.0", calendar2);
        System.out.println(guava2);
        System.out.println(guava2.hashCode());
        System.out.println(guava.equals(guava2));
    }

}
