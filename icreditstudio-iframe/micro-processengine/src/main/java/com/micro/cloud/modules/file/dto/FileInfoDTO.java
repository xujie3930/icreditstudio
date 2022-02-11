package com.micro.cloud.modules.file.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 〈文件信息〉
 *
 * @author roy
 * @create 2021/12/7
 * @since 1.0.0
 */
public class FileInfoDTO implements Serializable {
  private static final long serialVersionUID = 820187938189150815L;

  @ApiModelProperty(value = "project_id 项目id")
  private Integer projectId;

  @ApiModelProperty(value = "创建者")
  private String creator;

  @ApiModelProperty(value = "fileName")
  private String fileName;

  @ApiModelProperty(value = "filePath")
  private String filePath;

  @ApiModelProperty(value = "项目名称")
  private String projectName;

  @ApiModelProperty(value = "文件id")
  private int id;

  @ApiModelProperty(value = "文件类型")
  private String fileType;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;

  @ApiModelProperty(value = "文件名称")
  private String name;

  @ApiModelProperty(value = "文件url")
  private String url;

  @ApiModelProperty(value = "文件预览url")
  private String previewUrl;

  @ApiModelProperty(value = "文件大类 1，必备文件，2 流程文件 3 其他文件")
  private String fileLargeType;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public String getFileLargeType() {
		return fileLargeType;
	}

	public void setFileLargeType(String fileLargeType) {
		this.fileLargeType = fileLargeType;
	}

	@Override
	public String toString() {
		return "FileInfoDTO{" +
				"projectId=" + projectId +
				", creator='" + creator + '\'' +
				", fileName='" + fileName + '\'' +
				", filePath='" + filePath + '\'' +
				", projectName='" + projectName + '\'' +
				", id=" + id +
				", fileType='" + fileType + '\'' +
				", createTime=" + createTime +
				", name='" + name + '\'' +
				", url='" + url + '\'' +
				", previewUrl='" + previewUrl + '\'' +
				", fileLargeType='" + fileLargeType + '\'' +
				'}';
	}
}
