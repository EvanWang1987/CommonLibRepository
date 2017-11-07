package com.github.evan.common_utils.exception;

/**
 * Created by Evan on 2017/11/1.
 *
 * 删除目标文件失败异常
 *
 * 执行剪切文件时，如果复制成功，删除源文件失败时抛出的异常。
 *
 *
 */
public class DeleteTargetFileFailWhenRollbackCutFileException extends RuntimeException {

    public DeleteTargetFileFailWhenRollbackCutFileException(String targetFilePath) {
        super("剪切文件失败后，执行回滚操作时，剪切的源文件回滚后，但删除已复制过去的文件时发生了删除错误，导致已复制过去的文件未删除, 文件目录: " + targetFilePath);
    }
}
