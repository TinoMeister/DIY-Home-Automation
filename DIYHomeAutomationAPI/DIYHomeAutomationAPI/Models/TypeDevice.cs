namespace DIYHomeAutomationAPI.Models
{
    /// <summary>
    /// This class represents a Device's Type
    /// </summary>
    public class TypeDevice
    {
        /// <summary>
        /// Device's Type Id
        /// </summary>
        public int Id { get; set; }

        /// <summary>
        /// Device's Type Name
        /// </summary>
        public string Name { get; set; } = null!;
    }
}
